package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sw.melody.common.annotation.SysLog;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.common.validator.ValidatorUtils;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.docker.service.ProductService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
@Slf4j
@RestController
@RequestMapping("docker/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<ProductEntity> configList = productService.queryList(query);
        int total = productService.queryTotal(query);
        return R.ok().put("page", new PageUtils(configList, total, query.getLimit(), query.getPage()));
    }

    @RequestMapping("/getall")
    public R getAll() {
        Map map = new HashMap<>();
        List<ProductEntity> list = productService.queryList(map);
        return R.ok().put("list", list);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ProductEntity entity = productService.queryObject(id);
        return R.ok().put("product", entity);
    }

    @SysLog("保存产品")
    @RequestMapping("/save")
    public R save(@RequestBody ProductEntity entity){
        ValidatorUtils.validateEntity(entity);
        entity.setCreateTime(new Date());
        productService.save(entity);
        return R.ok();
    }

    @SysLog("修改产品")
    @RequestMapping("/update")
    public R update(@RequestBody ProductEntity entity){
        ValidatorUtils.validateEntity(entity);
        productService.update(entity);
        return R.ok();
    }

    @SysLog("删除产品")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        productService.deleteBatch(ids);
        return R.ok();
    }
}
