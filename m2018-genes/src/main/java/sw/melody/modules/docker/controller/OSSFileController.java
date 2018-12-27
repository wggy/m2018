package sw.melody.modules.docker.controller;

import com.aliyun.oss.internal.OSSDownloadOperation;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.OSSFileEntity;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.OSSFileService;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.docker.util.OSSFileCacheUtil;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.oss.cloud.OSSFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author ping
 * @create 2018-12-24 17:03
 **/
@Slf4j
@RestController
@RequestMapping("docker/ossfile")
public class OSSFileController extends SaveFile {

    @Autowired
    private OSSFileService ossFileService;
    @Autowired
    private SickService sickService;
    @Autowired
    private SampleService sampleService;


    private Object lockObj = new Object();

    @RequestMapping("/list")
    public List<OSSFileEntity> list() {
        return ossFileService.getList();
    }

    @RequestMapping("/download/{sickId}")
    public R download(@PathVariable("sickId") Long sickId, Long fileId) {

        if (sickId == null || fileId == null) {
            return R.error("参数不能为空");
        }
        OSSFileEntity entity = ossFileService.queryObject(fileId);
        if (entity == null) {
            throw new RRException("文件不存在");
        }
        SickEntity sickEntity = sickService.queryObject(sickId);
        String uploadFolderPath = getRealPath();
        String location = ConfigConstant.getFullPath(uploadFolderPath, sickEntity.getSickCode());

        String[] nameArray = StringUtils.splitPreserveAllTokens(entity.getPath(), "/");
        SampleEntity chunkInfo = new SampleEntity();
        chunkInfo.setUploadStatus(Constant.SampleStatus.Running.getStatus());
        chunkInfo.setUploadStartTime(new Date());
        chunkInfo.setOriginName(nameArray[nameArray.length - 1]);
        chunkInfo.setSickId(sickId);
        chunkInfo.setChunksNumber(0);
        chunkInfo.setUploadType("OSS");
        chunkInfo.setLocation(location);
        chunkInfo.setFileId(fileId);
        sampleService.save(chunkInfo);
        OSSFileCacheUtil.pushReq(chunkInfo.getId());

        return R.ok().put("sampleId", chunkInfo.getId());
    }


    @RequestMapping("/progress/{id}")
    public R progress(@PathVariable("id") Long id) throws IOException, ClassNotFoundException {
        if (id == null) {
            return R.error("参数不能为空");
        }
        SampleEntity entity = sampleService.queryObject(id);
        if (entity == null) {
            return R.error(-1, "记录不存在");
        }
        if (entity.getUploadStatus().equals(Constant.SampleStatus.Running.getStatus()) && "OSS".equals(entity.getUploadType())) {
            String cpFile = addFileSeparator(entity.getLocation()) + entity.getOriginName() + ".ucp";
            File file = new File(cpFile);
            if (!file.exists()) {
                return R.ok("文件不存在");
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object dcp = in.readObject();
            String json = new Gson().toJson(dcp);
            log.info(json);
            in.close();
            fileIn.close();
            return R.ok(json);
        }

        return R.error(1, "文件未在下载中");
    }


}
