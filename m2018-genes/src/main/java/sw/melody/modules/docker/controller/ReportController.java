package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.ReportEntity;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.ReportService;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 * @author ping
 */
@Slf4j
@RestController
@RequestMapping("docker/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<ReportEntity> sysOssList = reportService.queryList(query);
        int total = reportService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(sysOssList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping("/get_dy_url")
    public R getDyUrl() {
        return R.ok().put("list", reportService.queryDyUrlList());
    }
}
