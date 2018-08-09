package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.ProductEntity;

import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2018-08-09 10:25
 **/
@Slf4j
@RestController
@RequestMapping("docker/gene/search")
public class GeneSearchController {

    @RequestMapping("/sick_info")
    public R list(@RequestParam Map<String, Object> params) {

        return R.ok();
    }
}
