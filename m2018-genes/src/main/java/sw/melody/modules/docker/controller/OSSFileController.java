package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
import sw.melody.modules.docker.task.OSSFileLoadTask;
import sw.melody.modules.docker.task.PollLogFileTask;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private SysConfigService sysConfigService;

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
        OSSFileLoadTask.pushReq(chunkInfo.getId());

        return R.ok().put("sampleId", chunkInfo.getId());
    }


    @RequestMapping("/trigger/{sickId}")
    public R progress(@PathVariable("sickId") Long sickId, Long id1, Long id2) {
        if (id1 == null || id2 == null || sickId == null) {
            return R.error("参数不能为空");
        }
        OSSFileEntity file1 = ossFileService.queryObject(id1);
        OSSFileEntity file2 = ossFileService.queryObject(id2);
        SickEntity sick = sickService.queryObject(sickId);

        if (file1 == null || file2 == null || sick == null) {
            return R.error("记录不存在");
        }

        SampleEntity sample = new SampleEntity();
        sample.setOriginName("oss://jzdk20181201/" + file1.getPath());
        sample.setSecOriginName("oss://jzdk20181201/" + file2.getPath());
        sample.setSickId(sickId);
        sample.setFileId(id1);
        sample.setLocation(SaveFile.linkFileSeparator(getRealPath()) + sick.getSickCode());
        sample.setUploadStartTime(new Date());
        sample.setUploadStatus("success");
        sample.setUploadFinishTime(new Date());
        sample.setUploadType("OSS");
        sampleService.save(sample);

        try {
            if (checkScript(sample)) {
                String command = SaveFile.callShellCommand(sample.getLocation(), sample.getOriginName(), sample.getSecOriginName());
                log.info("command: {}", command);
                sample.setTriggerStartTime(new Date());
                sample.setTriggerStatus("success");
                sampleService.update(sample);
                triggerShell(command);
                log.info("样本调度成功，sampleID：{}", sample.getId());
                PollLogFileTask.pushReq(sickId);
                return R.ok();
            } else {
                log.error("样本校验失败，sampleID：{}", sample.getId());
                return R.error("样本校验失败");
            }
        } catch (Exception e) {
            sample.setTriggerStatus("fail");
            sample.setTriggerFinishTime(new Date());
            sampleService.update(sample);
            e.printStackTrace();
            return R.error("样本处理异常");
        }
    }


    boolean checkScript(SampleEntity entity) throws IOException, InterruptedException {
        String bashFilePath = sysConfigService.getValue(ConfigConstant.SAMPLE_SHELL_PATH);
        if (!new File(bashFilePath).exists()) {
            log.error("bash文件不存在");
            return false;
        }

        if (!new File(entity.getOriginName()).exists()) {
            log.error("样本1不存在");
            return false;
        }

        if (!StringUtils.isBlank(entity.getSecOriginName())) {
            if (!new File(entity.getSecOriginName()).exists()) {
                log.error("样本2不存在");
                return false;
            }
        }

        synchronized (lockObj) {
            File fileDirectory = new File(entity.getLocation());
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    return false;
                }
            }
        }

        String shellFileName = sysConfigService.getValue(ConfigConstant.Shell_Bwa_File);
        File targetBashFile = new File(SaveFile.linkFileSeparator(entity.getLocation()) + shellFileName);
        if (!targetBashFile.exists()) {
            Process cpFilePs = Runtime.getRuntime().exec("cp -pf " + bashFilePath + " " + entity.getLocation());
            if (cpFilePs.waitFor() != 0) {
                log.error("中间文件拷贝失败");
                return false;
            }
        }
        return true;
    }



}
