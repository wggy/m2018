package sw.melody.modules.docker.task;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.modules.docker.entity.OSSFileEntity;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.OSSFileService;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.oss.cloud.AliyunCloudStorageService;
import sw.melody.modules.oss.cloud.OSSFactory;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.File;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * @author ping
 * @create 2018-12-24 18:02
 **/
@Component
public class OSSFileLoadTask extends Thread implements InitializingBean, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(OSSFileLoadTask.class);
    private static final List<OSSFileEntity> ossFileRepo = Collections.synchronizedList(new ArrayList<>());
    private static final int rootId = 0;
    private static final String catalogFile = "D";
    private static final String justFile = "F";

    private static OSSFileService ossFileService;
    private static SickService sickService;
    private static SampleService sampleService;
    private static SysConfigService sysConfigService;
    private static Object lockObj = new Object();

    private static final LinkedBlockingDeque<Long> ossFileReqDeque = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true) {
            try {
                Long reqId = ossFileReqDeque.take();
                if (reqId != null) {
                    long start = System.currentTimeMillis();
                    SampleEntity sampleEntity = sampleService.queryObject(reqId);
                    if (sampleEntity == null) {
                        log.error("{} 该样本id无记录", reqId);
                        throw new Exception("查无样本记录");
                    }
                    OSSFileEntity ossFileEntity = ossFileService.queryObject(sampleEntity.getFileId());
                    if (ossFileEntity == null) {
                        log.error("{} 该文件id无记录", sampleEntity.getFileId());
                        throw new Exception("查无文件记录");
                    }
                    File fileDirectory = new File(sampleEntity.getLocation());
                    synchronized (lockObj) {
                        if (!fileDirectory.exists()) {
                            if (!fileDirectory.mkdirs()) {
                                throw new Exception("文件夹创建失败！路径为：" + sampleEntity.getLocation());
                            }
                        }
                    }
                    OSSFactory.build().download(addFileSeparator(sampleEntity.getLocation()) + sampleEntity.getOriginName(), ossFileEntity.getPath());
                    sampleEntity.setUploadStatus(Constant.SampleStatus.Success.getStatus());
                    sampleEntity.setUploadFinishTime(new Date());
                    sampleService.update(sampleEntity);
                    long end = System.currentTimeMillis();
                    log.info("{} 耗时：{}", sampleEntity.getOriginName(), (end - start));
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void pushReq(Long reqId) {
        ossFileReqDeque.add(reqId);
        log.info(">>>>>>>>>>>>>>> 下载文件请求：reqId:{}", reqId);
    }

    public static List<OSSFileEntity> getOssFileRepo() {
        return ossFileRepo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AliyunCloudStorageService aliyunService = (AliyunCloudStorageService) OSSFactory.build();
        List<Bucket> bucketList = aliyunService.getClient().listBuckets();

        String nextMarker = null;
        int maxKeys = 100;
        List<OSSObjectSummary> summaryList = new ArrayList<>();
        int i = 0;
        for (Bucket bucket : bucketList) {
            ObjectListing objectListing;
            do {
                objectListing = aliyunService.getClient().listObjects(new ListObjectsRequest(bucket.getName()).withMarker(nextMarker).withMaxKeys(maxKeys));
                List<OSSObjectSummary> ossObjectSummaryList = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : ossObjectSummaryList) {
                    i++;
                    System.out.println(i + " : " + s.getKey());
                }
                summaryList.addAll(ossObjectSummaryList);
                nextMarker = objectListing.getNextMarker();
            } while (objectListing.isTruncated());
        }

        List<OSSFileEntity> list = new ArrayList<>();
        summaryList.forEach(item -> list.add(OSSFileEntity.toFile(item)));
        Map<String, OSSFileEntity> repoMap = list.stream().collect(HashMap::new, (m, v) -> m.put(v.getPath(), v), HashMap::putAll);

        List<OSSFileEntity> rootList = list.stream().filter(item -> rootId == item.getLevel()).collect(Collectors.toList());
        ossFileService.saveBatch(rootList);
        saveChildren(repoMap, rootList);
        ossFileRepo.addAll(list);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ossFileService = applicationContext.getBean(OSSFileService.class);
        sickService = applicationContext.getBean(SickService.class);
        sampleService = applicationContext.getBean(SampleService.class);
        sysConfigService = applicationContext.getBean(SysConfigService.class);
        ossFileService.deleteAll();
    }

    void saveChildren(Map<String, OSSFileEntity> repoMap, List<OSSFileEntity> parentList) {
        List<OSSFileEntity> parentCatalogList = parentList.stream().filter(item -> catalogFile.equals(item.getFileType())).collect(Collectors.toList());
        for (OSSFileEntity entity : parentCatalogList) {
            List<OSSFileEntity> childrenList = getChildren(repoMap, entity);
            if (CollectionUtils.isNotEmpty(childrenList)) {
                ossFileService.saveBatch(childrenList);
                saveChildren(repoMap, childrenList);
            }
        }
    }

    List<OSSFileEntity> getChildren(Map<String, OSSFileEntity> repoMap, OSSFileEntity entity) {
        Set<String> repoKeySet = repoMap.keySet();
        List<OSSFileEntity> childrenList = new ArrayList<>();
        String[] parentKeyArray = StringUtils.splitPreserveAllTokens(entity.getPath(), "/");
        int parentLength = parentKeyArray.length;
        for (String key : repoKeySet) {
            String[] arrayKeys = StringUtils.splitPreserveAllTokens(key, "/");
            int childLength = arrayKeys.length;
            OSSFileEntity obj = repoMap.get(key);
            if (key.startsWith(entity.getPath()) && ((catalogFile.equals(obj.getFileType()) && parentLength + 1 == childLength)
                    || (justFile.equals(obj.getFileType()) && parentLength == childLength))) {
                obj.setParentId(entity.getId());
                childrenList.add(obj);
            }
        }
        return childrenList;
    }

    static String addFileSeparator(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RRException("路径为空");
        }
        if (path.endsWith(ConfigConstant.File_Separator)) {
            return path;
        }
        return path + ConfigConstant.File_Separator;
    }

}
