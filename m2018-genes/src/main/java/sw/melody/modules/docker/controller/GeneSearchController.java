package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.utils.PageUtils;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.docker.service.GeneSearchService;

import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2018-08-09 10:25
 **/
@Slf4j
@RestController
@RequestMapping("docker/gene_search")
public class GeneSearchController {

    @Autowired
    private GeneSearchService geneSearchService;

    @RequestMapping("/sick_info")
    public R sickInfo(@RequestParam Map<String, Object> params) {
        return R.ok().put("info", geneSearchService.querySickRelation(params));
    }

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<GeneSearchEntity> configList = geneSearchService.queryList(query);
        int total = geneSearchService.queryTotal(query);
        return R.ok().put("page", new PageUtils(configList, total, query.getLimit(), query.getPage()));
    }

}
