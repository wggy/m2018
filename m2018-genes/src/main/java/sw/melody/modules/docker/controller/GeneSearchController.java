package sw.melody.modules.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.Query;
import sw.melody.common.utils.R;
import sw.melody.common.utils.WorderToNewWordUtils;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.GeneSearchService;
import sw.melody.modules.docker.service.ProductService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.job.utils.Arith;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private SickService sickService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/sick_info")
    public R sickInfo(@RequestParam Map<String, Object> params) {
        return R.ok().put("info", geneSearchService.querySickRelation(params));
    }

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        String popfreqmax = query.get("popfreqmax") == null || "0".equals(query.get("popfreqmax")) ? "" : query.get("popfreqmax").toString();
        String exacAll = query.get("exacAll") == null || "0".equals(query.get("exacAll")) ? "" : query.get("exacAll").toString();
        String a1000gAll = query.get("a1000gAll") == null || "0".equals(query.get("a1000gAll")) ? "" : query.get("a1000gAll").toString();
        String exacEas = query.get("exacEas") == null || "0".equals(query.get("exacEas")) ? "" : query.get("exacEas").toString();

        query.remove("popfreqmax");
        query.remove("exacAll");
        query.remove("a1000gAll");
        query.remove("exacEas");
        if (StringUtils.isNotBlank(popfreqmax)) {
            query.put("popfreqmax", Arith.div(Double.valueOf(popfreqmax), 100d, 4) + "");
        }
        if (StringUtils.isNotBlank(exacAll)) {
            query.put("exacAll", Arith.div(Double.valueOf(exacAll), 100d, 4) + "");
        }
        if (StringUtils.isNotBlank(a1000gAll)) {
            query.put("a1000gAll", Arith.div(Double.valueOf(a1000gAll), 100d, 4) + "");
        }
        if (StringUtils.isNotBlank(exacEas)) {
            query.put("exacEas", Arith.div(Double.valueOf(exacEas), 100d, 4) + "");
        }
        List<GeneSearchEntity> list = geneSearchService.queryList(query);
        int totalCount = geneSearchService.queryTotal(query);
        String sickCode = query.get("sickCode").toString();
        SickEntity sickEntity = sickService.queryObjectByCode(sickCode);
        if (sickEntity == null) {
            return R.error("该病人未录入");
        }
        int noConditionCount = geneSearchService.queryTotalCount(sickEntity.getId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", list);
        returnMap.put("totalCount", totalCount);
        returnMap.put("pageSize", query.getLimit());
        returnMap.put("totalPage", (int) Math.ceil((double) totalCount / query.getLimit()));
        returnMap.put("currPage", query.getPage());
        returnMap.put("noConditionCount", noConditionCount);
        return R.ok().put("page", returnMap);
    }

    @RequestMapping("/report")
    public void report(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        if (params.get("ids") == null || params.get("productId") == null || params.get("sickCode") == null) {
            throw new RRException("参数错误");
        }
        String ids = params.get("ids").toString();
        String productId = params.get("productId").toString();
        String sickCode = params.get("sickCode").toString();
        List<String[]> list1 = new ArrayList<>();
        List<String[]> list2 = new ArrayList<>();

        if (params.get("ids") == null) {
            throw new RRException("参数错误");
        }

        String[] idsStrArray = ids.split("%2C");
        Long[] idsArray = new Long[idsStrArray.length];
        for (int i=0; i<idsStrArray.length; i++) {
            idsArray[i] = Long.parseLong(idsStrArray[i]);
        }
        List<GeneSearchEntity> list = geneSearchService.queryListByIds(idsArray);
        SickEntity sickEntity = sickService.queryObjectByCode(sickCode);
        ProductEntity productEntity = productService.queryObject(Long.parseLong(productId));
        if (sickEntity == null) {
            throw new RRException("查无该病人记录");
        }
        if (productEntity == null) {
            throw new RRException("查无该检验产品记录");
        }
        for (GeneSearchEntity entity : list) {
            String[] s = new String[7];
            s[0] = entity.getGeneRefgene();
            s[1] = "";
            s[2] = entity.getXrefRefgene();
            s[3] = entity.getMutationInfo();
            s[4] = entity.getAachangeRefgene();
            s[5] = entity.getSampleNameAttr();
            s[6] = "";
            list1.add(s);
        }
        String fileName = this.getClass().getClassLoader().getResource("healthy_template.docx").getPath();
        String userAgent = request.getHeader("User-Agent");
        WorderToNewWordUtils.geneReport(userAgent, fileName, "基因检测报告.docx", response,"test1", list1, list2);
    }

}
