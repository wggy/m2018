package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.docker.task.MergeFileTask;
import sw.melody.modules.docker.util.IsAllUploaded;
import sw.melody.modules.docker.util.SaveFile;

import java.util.Date;

/**
 * @author ping
 * @create 2018-12-28 18:21
 **/
@Slf4j
@RestController
@RequestMapping("docker/upload/sample")
public class UploadSampleController extends SaveFile {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private SickService sickService;

    @RequestMapping(value = "/file")
    public R fileUpload(String guid, String chunks, String chunk, String sickId,
                        String name, MultipartFile file) {

        if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(sickId) || StringUtils.isEmpty(name)) {
            return R.error("上传参数参数不能为空");
        }

        try {
            String fileName;

            int index;

            String uploadFolderPath = getRealPath();
            String ext = name.substring(name.lastIndexOf("."));
            SampleEntity chunkInfo = new SampleEntity();
            index = chunk == null ? 0 : Integer.parseInt(chunk);

            if (index == 0) {
                int chunksNumber = chunks == null ? 0 : Integer.parseInt(chunks);
                chunkInfo.setUploadStatus(Constant.SampleStatus.Running.getStatus());
                chunkInfo.setUploadStartTime(new Date());
                chunkInfo.setOriginName(guid + ext);
                chunkInfo.setSickId(Long.parseLong(sickId));
                chunkInfo.setChunksNumber(chunksNumber);
                chunkInfo.setUploadType("WEB");
                sampleService.save(chunkInfo);
            }

            SickEntity sickEntity = sickService.queryObject(Long.parseLong(sickId));
            if (sickEntity == null) {
                throw new RRException("查无该病患记录");
            }

            String fullPathNoFile = ConfigConstant.getFullPathNoFile(uploadFolderPath, sickEntity.getSickCode(), guid);
            String location = ConfigConstant.getFullPath(uploadFolderPath, sickEntity.getSickCode());
            //判断文件是否分块
            if (chunks != null) {
                fileName = String.valueOf(index) + ext;
                // 将文件分块保存到临时文件夹里，便于之后的合并文件
                saveFile(fullPathNoFile, fileName, file);
                // 验证所有分块是否上传成功，成功的话进行合并
                boolean allUploaded = IsAllUploaded.uploadedAll(guid, chunk, chunks, fullPathNoFile, fileName, ext);
                if (allUploaded) {
                    SampleEntity entity = sampleService.queryObjectByGuid(guid + ext);
                    if (entity == null) {
                        throw new RRException("服务器找不到文件");
                    }
                    MergeFileTask.pushReq(entity.getId());
                }
            } else {
                fileName = guid + ext;
                saveFile(location, fileName, file);
                SampleEntity entity = sampleService.queryObjectByGuid(fileName);
                if (entity == null) {
                    throw new RRException("服务器找不到文件");
                }
                entity.setUploadFinishTime(new Date());
                entity.setUploadStatus(Constant.SampleStatus.Success.getStatus());
                entity.setLocation(location);
                sampleService.update(entity);
            }
            return R.ok("{chunk: " + chunk + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error("上传失败");
        }

    }

}
