package sw.melody.modules.docker.controller;

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
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.oss.cloud.OSSFactory;
import sw.melody.modules.oss.entity.SysOssEntity;
import sw.melody.modules.sys.service.SysConfigService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/****
 * 样本管理
 */
@RestController
@RequestMapping("docker/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/list")
    @RequiresPermissions("docker:sample:query")
    public R list(@RequestParam Map<String, Object> params){
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
    public R info(@PathVariable("id") Long id){
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
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
        String url = getPath(prefix, suffix);

        //保存上传文件信息
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setLocation(url);
        sampleEntity.setUploadTime(new Date());
        sampleEntity.setSickId(id);
        sampleEntity.setOriginName(file.getOriginalFilename());
        sampleService.save(sampleEntity);

        return R.ok().put("url", url);
    }

    private String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

}
