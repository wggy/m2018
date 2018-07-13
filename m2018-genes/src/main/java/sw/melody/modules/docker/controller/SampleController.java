package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
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
import sw.melody.modules.sys.service.SysConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
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

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
        SickEntity sickEntity = sickService.queryObject(id);
        if (sickEntity == null) {
            throw new RRException("查无该病患记录");
        }
        String shortPath = ConfigConstant.getShortPath(sickEntity.getSickCode(), suffix);
        String fullPath = ConfigConstant.getFullPath(prefix, shortPath);

        SampleEntity sampleEntity = sampleService.queryObjectByLocationSick(shortPath, id);

        File newFile = IOUtils.uploadToFile(file, fullPath);
        if (newFile == null) {
            throw new RRException("上传文件失败，请联系管理员");
        }

        if (sampleEntity == null) {
            //保存上传文件信息
            sampleEntity = new SampleEntity();
            sampleEntity.setLocation(shortPath);
            sampleEntity.setUploadTime(new Date());
            sampleEntity.setSickId(id);
            sampleEntity.setOriginName(file.getOriginalFilename());
            sampleService.save(sampleEntity);
        } else {
            sampleEntity.setOriginName(file.getOriginalFilename());
            sampleEntity.setUploadTime(new Date());
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
        if (Constant.SampleStatus.Running.getStatus().equals(sampleEntity.getHandlerStatus())) {
            throw new RRException("正在解析样本中...");
        } else if (StringUtils.isEmpty(sampleEntity.getHandlerStatus())) {
            sampleEntity.setHandlerStatus(Constant.SampleStatus.Running.getStatus());
            sampleEntity.setTriggerTime(new Date());
            sampleService.update(sampleEntity);
        }
        Thread exeThread = new Thread(() -> {
            String bash = sysConfigService.getValue(ConfigConstant.SAMPLE_SHELL_PATH);
            String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
            SampleEntity entity = sampleService.queryObject(id);
            Process ps = null;
            try {
                ps = Runtime.getRuntime().exec(bash + " " + ConfigConstant.getFullPath(prefix, entity.getLocation()));
//                ps = Runtime.getRuntime().exec("jps -m");
                int status = ps.waitFor();
                if (status != 0) {
                    log.error("Failed to call shell's command ");
                    sampleEntity.setHandlerStatus(Constant.SampleStatus.Fail.getStatus());
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        log.info(line);
                    }
                    sampleEntity.setHandlerStatus(Constant.SampleStatus.Success.getStatus());
                }
            } catch (Exception e) {
                sampleEntity.setHandlerStatus(Constant.SampleStatus.Fail.getStatus());
                e.printStackTrace();
            }
            sampleEntity.setHandlerTime(new Date());
            sampleService.update(sampleEntity);
        });
        Runtime.getRuntime().addShutdownHook(exeThread);
        exeThread.start();
        return R.ok("调度成功");

    }
}