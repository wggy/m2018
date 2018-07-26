package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.SampleEntity;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
@Slf4j
@RestController
@RequestMapping("docker/report")
public class ReportController {
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        return R.ok();
    }
}
