package sw.melody.modules.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.util.IsAllUploaded;
import sw.melody.modules.docker.util.SaveFile;

/**
 * @author ping
 * @create 2018-08-27 15:47
 **/
@RestController
@RequestMapping("docker/upload/")
public class UploadLargeFileController extends SaveFile {

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/check_md5", method = RequestMethod.POST)
    public R bigFileUpload(String md5) {
        SampleEntity entity = sampleService.queryObjectByMd5(md5);
        if (entity != null) {
            return R.error("this file is exist");
        } else {
            return R.ok("this file is not exist");
        }
    }

    @RequestMapping(value = "/large_file")
    public R fileUpload(String guid,
                             String md5value,
                             String chunks,
                             String chunk,
                             String id,
                             String name,
                             String type,
                             String lastModifiedDate,
                             int size,
                             MultipartFile file) {
        String fileName;
        try {
            int index;
            String uploadFolderPath = getRealPath();

            String mergePath = uploadFolderPath + guid + "/";
            String ext = name.substring(name.lastIndexOf("."));

            //判断文件是否分块
            if (chunks != null) {
                index = Integer.parseInt(chunk);
                fileName = String.valueOf(index) + ext;
                // 将文件分块保存到临时文件夹里，便于之后的合并文件
                saveFile(mergePath, fileName, file);
                // 验证所有分块是否上传成功，成功的话进行合并
                IsAllUploaded.uploaded(md5value, guid, chunk, chunks, uploadFolderPath, fileName, ext);
            } else {
                fileName = guid + ext;
                //上传文件没有分块的话就直接保存
                saveFile(uploadFolderPath, fileName, file);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error("上传失败");
        }

        return R.ok();
    }
}
