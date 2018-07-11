package sw.melody.modules.docker.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sw.melody.common.annotation.SysLog;
import sw.melody.common.utils.DateUtils;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.common.validator.ValidatorUtils;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SickService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("docker/sick")
public class SickController {

    @Autowired
    private SickService sickService;

    @RequestMapping("/list")
    @RequiresPermissions("docker:sick:query")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<SickEntity> sysOssList = sickService.queryList(query);
        int total = sickService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(sysOssList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 病人信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("docker:sick:query")
    public R info(@PathVariable("id") Long id){
        SickEntity sick = sickService.queryObject(id);
        return R.ok().put("info", sick);
    }

    @SysLog("新增病人信息")
    @RequestMapping("/save")
    @RequiresPermissions("docker:sick:edit")
    public R save(@RequestBody SickEntity entity){
        ValidatorUtils.validateEntity(entity);
        // 生成病人编码
        String sickCode = "HY" + DateUtils.format(new Date(), DateUtils.DATE_TIME_STAMP_PATTERN);
        entity.setSickCode(sickCode);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        sickService.save(entity);
        return R.ok();
    }

    @SysLog("修改病人信息")
    @RequestMapping("/update")
    @RequiresPermissions("docker:sick:edit")
    public R update(@RequestBody SickEntity entity){
        ValidatorUtils.validateEntity(entity);
        entity.setUpdateTime(new Date());
        sickService.update(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("docker:sick:edit")
    public R delete(@RequestBody Long[] ids){
        sickService.deleteBatch(ids);
        return R.ok();
    }
}
