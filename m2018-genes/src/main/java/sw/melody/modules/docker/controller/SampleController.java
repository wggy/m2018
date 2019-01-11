package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.annotation.SysLog;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.*;
import sw.melody.common.utils.Constant.SampleStatus;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.docker.task.TriggerScriptTask;
import sw.melody.modules.docker.util.MoreLogUtil;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.job.task.GeneIndelTask;
import sw.melody.modules.job.task.GeneSnpTask;
import sw.melody.modules.sys.entity.SysConfigEntity;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * 样本管理
 * @author wange
 */
@Slf4j
@RestController
@RequestMapping("docker/sample")
public class SampleController extends SaveFile {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SickService sickService;
    @Autowired
    private GeneSnpTask geneSnpTask;
    @Autowired
    private GeneIndelTask geneIndelTask;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SampleEntity> sysOssList = sampleService.queryList(query);
        int total = sampleService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(sysOssList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 病人信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SampleEntity sampleEntity = sampleService.queryObject(id);
        return R.ok().put("info", sampleEntity);
    }

    @RequestMapping("/count/{id}")
    public R count(@PathVariable("id") Long id) {
        return R.ok().put("count", sampleService.queryTotalBySickId(id));
    }


    /**
     * 上传文件
     */
    @SysLog("上传样本")
    @RequestMapping("/upload/{id}")
    public R upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || id == null || file.isEmpty()) {
            throw new RRException("上传文件或参数不能为空");
        }

        // 上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        suffix = suffix.replace(".", "");
        // 匹配文件名
        Map<String, Object> map = new HashMap<>();
        map.put("key", ConfigConstant.File_Allowed);
        List<SysConfigEntity> configList = sysConfigService.queryList(map);
        if (CollectionUtils.isNotEmpty(configList)) {
            String allowFile = configList.get(0).getValue();
            if (StringUtils.isEmpty(suffix) || StringUtils.isEmpty(allowFile) || !allowFile.contains(suffix)) {
                throw new RRException("请上传符合条件的文件：" + allowFile);
            }
        }
        String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
        SickEntity sickEntity = sickService.queryObject(id);
        if (sickEntity == null) {
            throw new RRException("查无该病患记录");
        }
        String shortPath = ConfigConstant.getShortPath(sickEntity.getSickCode(), suffix);
        String fullPath = ConfigConstant.getFullPath(prefix, shortPath);
        String fullPathNoFile = ConfigConstant.getFullPathNoFile(prefix);

        SampleEntity sampleEntity = sampleService.queryObjectByLocationSick(shortPath, id);

        File newFile = IOUtils.uploadToFile(file, fullPath);
        if (newFile == null) {
            throw new RRException("上传文件失败，请联系管理员");
        }
        String bashFilePath = sysConfigService.getValue(ConfigConstant.SAMPLE_SHELL_PATH);
        File bashFile = new File(bashFilePath);
        if (bashFile == null) {
            throw new RRException("bash文件不存在");
        }
        Process ps = Runtime.getRuntime().exec("cp -pf " + bashFilePath + " " + fullPathNoFile);
        int status = ps.waitFor();
        if (status != 0) {
            throw new RRException("中间文件拷贝失败");
        }
        if (sampleEntity == null) {
            //保存上传文件信息
            sampleEntity = new SampleEntity();
            sampleEntity.setLocation(shortPath);
            sampleEntity.setUploadStartTime(new Date());
            sampleEntity.setSickId(id);
            sampleEntity.setOriginName(file.getOriginalFilename());
            sampleService.save(sampleEntity);
        } else {
            sampleEntity.setOriginName(file.getOriginalFilename());
            sampleEntity.setUploadStartTime(new Date());
            sampleService.update(sampleEntity);
        }

        return R.ok().put("url", shortPath);
    }

    @SysLog("调度样本")
    @RequestMapping("/execute/{id}")
    public R execute(@PathVariable("id") Long id) throws Exception {

        if (id == null) {
            return R.error("参数错误");
        }

        SampleEntity sampleEntity = sampleService.queryObject(id);
        checkStatus(sampleEntity);

        String targetFileName = sampleEntity.getOriginName();
        String bashFilePath = sysConfigService.getValue(ConfigConstant.SAMPLE_SHELL_PATH);
        File bashFile = new File(bashFilePath);
        if (!bashFile.exists()) {
            throw new RRException("bash文件不存在");
        }

        File fullPathFile = new File(addFileSeparator(sampleEntity.getLocation()) + sampleEntity.getOriginName());
        if (!fullPathFile.exists()) {
            throw new RRException("样本1不存在");
        }

        String secFileName = "";
        if (!StringUtils.isBlank(sampleEntity.getSecOriginName())) {
            File secFullPathFile = new File(addFileSeparator(sampleEntity.getLocation()) + sampleEntity.getSecOriginName());
            if (!secFullPathFile.exists()) {
                throw new RRException("样本2不存在");
            }
            secFileName = sampleEntity.getSecOriginName();
        }

        String fullPathNoFile = sampleEntity.getLocation();
        String shellFileName = sysConfigService.getValue(ConfigConstant.Shell_Bwa_File);
        File targetBashFile = new File(addFileSeparator(fullPathNoFile) + shellFileName);
        if (!targetBashFile.exists()) {
            Process cpFilePs = Runtime.getRuntime().exec("cp -pf " + bashFilePath + " " + fullPathNoFile);
            int cpFileStatus = cpFilePs.waitFor();
            if (cpFileStatus != 0) {
                throw new RRException("中间文件拷贝失败");
            }
        }

        String command = getCommand(fullPathNoFile, targetFileName, secFileName);
        log.info("command: {}", command);
        Thread exeThread = new TriggerThread(command, sampleEntity);
        Runtime.getRuntime().addShutdownHook(exeThread);
        exeThread.start();
        return R.ok("调度成功");

    }

    private void checkStatus(SampleEntity sampleEntity) {
        if (sampleEntity == null) {
            throw new RRException("查无上传记录");
        }
        if (SampleStatus.Running.getStatus().equals(sampleEntity.getTriggerStatus())) {
            throw new RRException("正在解析样本中...");
        } else if (SampleStatus.Success.getStatus().equals(sampleEntity.getTriggerStatus())) {
            throw new RRException("样本已解析完成...");
        } else {
            sampleEntity.setTriggerStatus(Constant.SampleStatus.Running.getStatus());
            sampleEntity.setTriggerStartTime(new Date());
            sampleService.update(sampleEntity);
        }
    }

    private String getCommand(String fullPathNoFile, String targetFileName, String secFileName) {
        String nohupShell = sysConfigService.getValue(ConfigConstant.Shell_Bwa);
        if (StringUtils.isBlank(secFileName)) {
            return "cd " + fullPathNoFile + " &&  " + nohupShell + " " + targetFileName + " > " + targetFileName + ".out 2>&1 &";
        }
        return "cd " + fullPathNoFile + " &&  " + nohupShell  + " " + targetFileName + " " + secFileName + " > " + targetFileName + ".out 2>&1 &";
    }

    @SysLog("样本入库")
    @RequestMapping("/store/{id}")
    public R store(@PathVariable("id") Long id) throws Exception {
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            throw new RRException("查无上传记录");
        }
        if (!Constant.SampleStatus.Success.getStatus().equals(sampleEntity.getTriggerStatus())) {
            throw new RRException("样本未成功解析，无法入库...");
        }
        if (Constant.SampleStatus.Running.getStatus().equals(sampleEntity.getStoreStatus())) {
            throw new RRException("样本入库中...");
        } else if (Constant.SampleStatus.Success.getStatus().equals(sampleEntity.getStoreStatus())) {
            throw new RRException("样本已入库...");
        }
        sampleEntity.setStoreStatus(Constant.SampleStatus.Running.getStatus());
        sampleEntity.setStoreStartTime(new Date());
        sampleService.update(sampleEntity);

        String location = sampleEntity.getLocation();
        String fileName = sampleEntity.getOriginName().substring(0, sampleEntity.getOriginName().indexOf("."));
        String indelPath = addFileSeparator(location).concat(fileName).concat(ConfigConstant.Result_Indel_File_Prefix);
        String snpPath = addFileSeparator(location).concat(fileName).concat(ConfigConstant.Result_Snp_File_Prefix);
        Long sickId = sampleEntity.getSickId();
        try {
            new StoreThread(sampleEntity, indelPath, snpPath, sickId).start();
            return R.ok("开始入库成功...");
        } catch (Exception e) {
            sampleEntity.setStoreStatus(SampleStatus.Fail.getStatus());
            sampleEntity.setStoreFinishTime(new Date());
            sampleService.update(sampleEntity);
            e.printStackTrace();
            return R.ok("入库失败...");
        }
    }

    @SysLog("调度样本")
    @RequestMapping("/merge")
    public R merge(@RequestBody Long[] ids) throws Exception {
        if (ids == null || ids.length != 2) {
            return R.error("参数错误，请传入两条记录");
        }

        SampleEntity frtSample = sampleService.queryObject(ids[0]);
        SampleEntity secSample = sampleService.queryObject(ids[1]);
        if (frtSample == null || secSample == null) {
            return R.error("样本不存在");
        }
        if (!SampleStatus.Success.getStatus().equals(frtSample.getUploadStatus())
                || !SampleStatus.Success.getStatus().equals(secSample.getUploadStatus())) {
            return R.error("您选择的记录未成功上传");
        }
        if (frtSample.getSickId().intValue() != secSample.getSickId().intValue()) {
            return R.error("您选择的记录归属不同病人");
        }

        frtSample.setSecOriginName(secSample.getOriginName());
        sampleService.update(frtSample);
        sampleService.deleteByFlag(secSample.getId());
        return R.ok("合并成功");
    }


    @RequestMapping("/more/{id}")
    public R moreLog(@PathVariable("id") Long id, Integer fromLine) throws Exception {
        if (id == null) {
            return R.error("参数错误");
        }
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            return R.error("该样本不存在");
        }
        String logFileName = addFileSeparator(sampleEntity.getLocation()).concat(sampleEntity.getOriginName()).concat(".out");
        MoreLogUtil.LogResult logResult = MoreLogUtil.readLog(logFileName, fromLine);

        if (logResult != null && logResult.getFromLineNum() > logResult.getToLineNum()) {
            logResult.setEnd(true);
        }
        return R.ok().put("log", logResult);
    }

    @RequestMapping("/del_file/{id}")
    public R deleteFile(@PathVariable("id") Long id) throws Exception {
        if (id == null) {
            return R.error("参数错误");
        }
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            return R.error("该样本不存在");
        }
        cleanUserDirectory(sampleEntity);
        sampleService.resetTriggerStatus(sampleEntity.getId());
        return R.ok();
    }

    @RequestMapping("/del_mid_file/{id}")
    public R deleteMidFile(@PathVariable("id") Long id) throws Exception {
        if (id == null) {
            return R.error("参数错误");
        }
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            return R.error("该样本不存在");
        }
        if (StringUtils.isBlank(sampleEntity.getLocation())) {
            return R.error("该样本文件不完整或未合并，请清空目录重新上传");
        }
        File directory = new File(sampleEntity.getLocation());
        String originName = sampleEntity.getOriginName();
        String secOriginName = sampleEntity.getSecOriginName();
        boolean isSecFile = false;
        if (StringUtils.isNotBlank(secOriginName)) {
            isSecFile = true;
        }
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File item : files) {
                if (isSecFile) {
                    if (!originName.equals(item.getName()) && !secOriginName.equals(item.getName())) {
                        FileUtils.forceDelete(item);
                    }
                } else {
                    if (!originName.equals(item.getName())) {
                        FileUtils.forceDelete(item);
                    }
                }

            }
        }
        sampleService.resetTriggerStatus(sampleEntity.getId());
        return R.ok();
    }

    @RequestMapping("/del_record/{id}")
    public R deleteRecord(@PathVariable("id") Long id) throws Exception {
        if (id == null) {
            return R.error("参数错误");
        }
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            return R.error("该样本不存在");
        }
        cleanUserDirectory(sampleEntity);
        sampleService.delete(id);
        return R.ok();
    }

    private void cleanUserDirectory(SampleEntity sampleEntity) throws IOException {
        String location = sampleEntity.getLocation();
        if (StringUtils.isBlank(sampleEntity.getLocation())) {
            SickEntity sick = sickService.queryObject(sampleEntity.getSickId());
            if (sick == null) {
                throw new RRException("该病人记录不存在");
            }
            location = addFileSeparator(sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX)) + sick.getSickCode();
        }
        FileUtils.deleteDirectory(new File(location));
    }

    private class TriggerThread extends Thread {

        private String command;
        private SampleEntity sampleEntity;

        public TriggerThread(String command, SampleEntity sampleEntity) {
            this.command = command;
            this.sampleEntity = sampleEntity;
            this.setName("trigger-thread-" + sampleEntity.getId());
        }

        @Override
        public void run() {
            Process ps;
            try {
                ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
                int status = ps.waitFor();
                log.info("status: {}", status);
                if (status != 0) {
                    log.error("Failed to call shell's command ");
                    sampleEntity.setTriggerStatus(SampleStatus.Fail.getStatus());
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        log.info(line);
                    }
                    sampleEntity.setTriggerStatus(SampleStatus.Running.getStatus());
                    br.close();
                    TriggerScriptTask.pushReq(sampleEntity.getId());
                }
            } catch (Exception e) {
                sampleEntity.setTriggerStatus(Constant.SampleStatus.Fail.getStatus());
                e.printStackTrace();
            }
            sampleEntity.setTriggerFinishTime(new Date());
            sampleService.update(sampleEntity);
        }
    }

    private class StoreThread extends Thread {
        private SampleEntity sampleEntity;
        private String indelPath;
        private String snpPath;
        private Long sickId;

        public StoreThread(SampleEntity sampleEntity, String indelPath, String snpPath, Long sickId) {
            this.sampleEntity = sampleEntity;
            this.indelPath = indelPath;
            this.snpPath = snpPath;
            this.sickId = sickId;
            this.setName("store-thread-" + sampleEntity.getId());
        }

        @Override
        public void run() {
            log.info("indel store: {}", indelPath);
            try {
                geneIndelTask.parse(indelPath, sickId);
                log.info("snp store: {}", snpPath);
                geneSnpTask.parse(snpPath, sickId);
                sampleEntity.setStoreStatus(SampleStatus.Success.getStatus());
                sampleEntity.setStoreFinishTime(new Date());
                sampleService.update(sampleEntity);
            } catch (Exception e) {
                e.printStackTrace();
                sampleEntity.setStoreStatus(SampleStatus.Fail.getStatus());
                sampleEntity.setStoreFinishTime(new Date());
                sampleService.update(sampleEntity);
            }
        }
    }
}