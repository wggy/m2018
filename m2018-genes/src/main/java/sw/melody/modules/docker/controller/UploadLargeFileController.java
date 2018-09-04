package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant.SampleStatus;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.docker.util.IsAllUploaded;
import sw.melody.modules.docker.util.SaveFile;

import java.util.Date;

/**
 * @author ping
 * @create 2018-08-27 15:47
 **/
@Slf4j
@RestController
@RequestMapping("docker/upload/")
public class UploadLargeFileController extends SaveFile {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private SickService sickService;

    @RequestMapping(value = "/check_md5", method = RequestMethod.POST)
    public R bigFileUpload(String fileMd5, String fileName, String fileID) {
        SampleEntity entity = sampleService.queryObjectByMd5(fileMd5);
        if (entity == null) {
            return R.ok("this file is not exist");
        }
        return R.error("this file is exist");
    }

    @RequestMapping(value = "/large_file")
    public R fileUpload(String guid, String md5value, String chunks, String chunk, Long sickId,
                        String name, MultipartFile file) {
        try {
            String fileName;

            if (StringUtils.isBlank(md5value)) {
                log.error("md5 is null");
            }
            int index;

            String uploadFolderPath = getRealPath();
            String ext = name.substring(name.lastIndexOf("."));
            SampleEntity chunkInfo = new SampleEntity();
            index = chunk == null ? 0 : Integer.parseInt(chunk);

            if (index == 0) {
                chunkInfo.setUploadStatus(SampleStatus.Running.getStatus());
                chunkInfo.setUploadStartTime(new Date());
                chunkInfo.setOriginName(guid + ext);
                chunkInfo.setSickId(sickId);
                chunkInfo.setMd5(md5value);
                sampleService.save(chunkInfo);
            }

            SickEntity sickEntity = sickService.queryObject(sickId);
            if (sickEntity == null) {
                throw new RRException("查无该病患记录");
            }

            String shortPath = ConfigConstant.getShortPath(sickEntity.getSickCode(), guid, ext);
            String fullPath = ConfigConstant.getFullPath(uploadFolderPath, shortPath);
            String fullPathNoFile = ConfigConstant.getFullPathNoFile(uploadFolderPath, sickEntity.getSickCode(), guid);


            //判断文件是否分块
            if (chunks != null) {
                fileName = String.valueOf(index) + ext;
                // 将文件分块保存到临时文件夹里，便于之后的合并文件
                saveFile(fullPathNoFile, fileName, file);
                // 验证所有分块是否上传成功，成功的话进行合并
                boolean allUploaded = IsAllUploaded.uploadedAll(md5value, guid, chunk, chunks, fullPathNoFile, fileName, ext);
                if (allUploaded) {
                    SampleEntity entity = sampleService.queryObjectByMd5(md5value);
                    if (entity == null) {
                        throw new RRException("服务器找不到文件");
                    }
                    entity.setUploadFinishTime(new Date());
                    entity.setUploadStatus(SampleStatus.Success.getStatus());
                    entity.setLocation(fullPath);
                    entity.setMd5(md5value);
                    sampleService.update(entity);
                }
            } else {
                fileName = guid + ext;
                //上传文件没有分块的话就直接保存
                saveFile(addFileSeparator(uploadFolderPath) + sickEntity.getSickCode(), fileName, file);
                SampleEntity entity = sampleService.queryObjectByMd5(md5value);
                if (entity == null) {
                    throw new RRException("服务器找不到文件");
                }
                entity.setUploadFinishTime(new Date());
                entity.setUploadStatus(SampleStatus.Success.getStatus());
                entity.setLocation(fullPath);
                entity.setMd5(md5value);
                sampleService.update(entity);
            }
            return R.ok("{chunk: " + chunk + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error("上传失败");
        }

    }
}
