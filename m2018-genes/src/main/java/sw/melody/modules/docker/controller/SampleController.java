package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.annotation.SysLog;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.*;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.job.task.GeneIndelTask;
import sw.melody.modules.job.task.GeneSnpTask;
import sw.melody.modules.sys.entity.SysConfigEntity;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * 样本管理
 */
@Slf4j
@RestController
@RequestMapping("docker/sample")
public class SampleController {

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
    @RequiresPermissions("docker:sample:query")
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
    @RequiresPermissions("docker:sample:query")
    public R info(@PathVariable("id") Long id) {
        SampleEntity sampleEntity = sampleService.queryObject(id);
        return R.ok().put("info", sampleEntity);
    }


    /**
     * 上传文件
     */
    @SysLog("上传病患样本")
    @RequestMapping("/upload/{id}")
    @RequiresPermissions("docker:sample:edit")
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

    @SysLog("解析病患样本")
    @RequestMapping("/execute/{id}")
    @RequiresPermissions("docker:sample:edit")
    public R execute(@PathVariable("id") Long id) throws Exception {

        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            throw new RRException("查无上传记录");
        }
        if (Constant.SampleStatus.Running.getStatus().equals(sampleEntity.getTriggerStatus())) {
            throw new RRException("正在解析样本中...");
        } else if (Constant.SampleStatus.Success.getStatus().equals(sampleEntity.getTriggerStatus())) {
            throw new RRException("样本已解析完成...");
        } else if (StringUtils.isEmpty(sampleEntity.getTriggerStatus())) {
            sampleEntity.setTriggerStatus(Constant.SampleStatus.Running.getStatus());
            sampleEntity.setTriggerStartTime(new Date());
            sampleService.update(sampleEntity);
        }
        Thread exeThread = new Thread(() -> {
            String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
            SampleEntity entity = sampleService.queryObject(id);
            String fullPath = ConfigConstant.getFullPath(prefix, entity.getLocation());
            File fullPathFile = new File(fullPath);
            if (fullPathFile == null) {
                log.error("fullPathFile 不存在。。。。");
                return;
            }
//            String bwaFile = fullPathFile.getParent() + ConfigConstant.File_Separator + ConfigConstant.Shell_Bwa;
            Process ps = null;
            String command = "cd " + fullPathFile.getParent() + " &&  nohup ./" + ConfigConstant.Shell_Bwa + " " + fullPathFile.getName() + " >log.out 2>&1 &";
            log.info("command: {}", command);
            try {
                ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
                int status = ps.waitFor();
                log.info("status: {}", status);
                if (status != 0) {
                    log.error("Failed to call shell's command ");
                    sampleEntity.setTriggerStatus(Constant.SampleStatus.Fail.getStatus());
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        log.info(line);
                    }
                    sampleEntity.setTriggerStatus(Constant.SampleStatus.Success.getStatus());
                    br.close();
                }
            } catch (Exception e) {
                sampleEntity.setTriggerStatus(Constant.SampleStatus.Fail.getStatus());
                e.printStackTrace();
            }
            sampleEntity.setTriggerFinishTime(new Date());
            sampleService.update(sampleEntity);
        });
        Runtime.getRuntime().addShutdownHook(exeThread);
        exeThread.start();
        return R.ok("调度成功");

    }

    @SysLog("样本入库")
    @RequestMapping("/store/{id}")
    @RequiresPermissions("docker:sample:edit")
    public R store(@PathVariable("id") Long id) throws Exception {
        SampleEntity sampleEntity = sampleService.queryObject(id);
        if (sampleEntity == null) {
            throw new RRException("查无上传记录");
        }
        if (!Constant.SampleStatus.Success.getStatus().equals(sampleEntity.getStoreStatus())) {
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

        String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
        String fullPath = ConfigConstant.getFullPath(prefix, sampleEntity.getLocation());
        String indelPath = fullPath.substring(0, fullPath.indexOf(".")) + ConfigConstant.Result_Indel_File_Prefix;
        String snpPath = fullPath.substring(0, fullPath.indexOf(".")) + ConfigConstant.Result_Snp_File_Prefix;
        try {
            log.info("indel store: {}", indelPath);
            geneSnpTask.parse(indelPath);
            log.info("snp store: {}", snpPath);
            geneIndelTask.parse(snpPath);
            sampleEntity.setStoreStatus(Constant.SampleStatus.Success.getStatus());
            sampleEntity.setStoreFinishTime(new Date());
            return R.ok("入库成功...");
        } catch (Exception e) {
            sampleEntity.setStoreStatus(Constant.SampleStatus.Fail.getStatus());
            sampleEntity.setStoreFinishTime(new Date());
            e.printStackTrace();
            return R.ok("入库失败...");
        }
    }

}