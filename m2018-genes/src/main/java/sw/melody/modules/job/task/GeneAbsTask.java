package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sw.melody.common.exception.RRException;
import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.entity.SnpInfoEntity;
import sw.melody.modules.job.service.SnpFormatService;
import sw.melody.modules.job.service.SnpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public abstract class GeneAbsTask {

    // 表头开始标记

    private static final String BEGIN = "#CHROM";
    private static final String TAB = "\t";
    private static final String INFO_SIGN = ";";
    private static final String EQUAL_SIGN = "=";
    private static final String EMPTY_VALUE = ".";

    // 固定表头

    private static String[] fixed_cols = new String[]{"CHROM", "POS", "ID", "REF", "ALT", "QUAL", "FILTER", "INFO"};
    private static final Map<String, String> cols_map = new HashMap<>();
    private static final Map<String, Integer> leng_map = new HashMap<>();

    static {
        cols_map.put("func.refgene", "func_refgene");
        cols_map.put("gene.refgene", "gene_refgene");
        cols_map.put("xref.refgene", "xref_refgene");
        cols_map.put("genedetail.refgene", "genedetail_refgene");
        cols_map.put("exonicfunc.refgene", "exonicfunc_refgene");
        cols_map.put("aachange.refgene", "aachange_refgene");
        cols_map.put("1000g_all", "a1000g_all");
        cols_map.put("1000g_afr", "a1000g_afr");
        cols_map.put("1000g_amr", "a1000g_amr");
        cols_map.put("1000g_eas", "a1000g_eas");
        cols_map.put("1000g_eur", "a1000g_eur");
        cols_map.put("1000g_sas", "a1000g_sas");
        cols_map.put("fathmm-mkl_coding_score", "fathmm_mkl_coding_score");
        cols_map.put("fathmm-mkl_coding_pred", "fathmm_mkl_coding_pred");
        cols_map.put("gerp++_rs", "gerp_rs");

        leng_map.put("ac", 0);
        leng_map.put("af", 0);
        leng_map.put("an", 0);
        leng_map.put("dp", 0);
        leng_map.put("excesshet", 0);
        leng_map.put("fs", 0);
        leng_map.put("mleac", 0);
        leng_map.put("mleaf", 0);
        leng_map.put("mq", 0);
        leng_map.put("qd", 0);
        leng_map.put("sor", 0);
        leng_map.put("annovar_date", 0);
        leng_map.put("func_refgene", 0);
        leng_map.put("gene_refgene", 0);
        leng_map.put("genedetail_refgene", 0);
        leng_map.put("exonicfunc_refgene", 0);
        leng_map.put("aachange_refgene", 0);
        leng_map.put("xref_refgene", 0);
        leng_map.put("cytoband", 0);
        leng_map.put("avsnp147", 0);
        leng_map.put("clinsig", 0);
        leng_map.put("clndbn", 0);
        leng_map.put("clnacc", 0);
        leng_map.put("clndsdb", 0);
        leng_map.put("clndsdbid", 0);
        leng_map.put("popfreqmax", 0);
        leng_map.put("a1000g_all", 0);
        leng_map.put("a1000g_afr", 0);
        leng_map.put("a1000g_amr", 0);
        leng_map.put("a1000g_eas", 0);
        leng_map.put("a1000g_eur", 0);
        leng_map.put("a1000g_sas", 0);
        leng_map.put("exac_all", 0);
        leng_map.put("exac_afr", 0);
        leng_map.put("exac_amr", 0);
        leng_map.put("exac_eas", 0);
        leng_map.put("exac_fin", 0);
        leng_map.put("exac_nfe", 0);
        leng_map.put("exac_oth", 0);
        leng_map.put("exac_sas", 0);
        leng_map.put("esp6500siv2_all", 0);
        leng_map.put("esp6500siv2_aa", 0);
        leng_map.put("esp6500siv2_ea", 0);
        leng_map.put("cg46", 0);
        leng_map.put("sift_score", 0);
        leng_map.put("sift_pred", 0);
        leng_map.put("polyphen2_hdiv_score", 0);
        leng_map.put("polyphen2_hdiv_pred", 0);
        leng_map.put("polyphen2_hvar_score", 0);
        leng_map.put("polyphen2_hvar_pred", 0);
        leng_map.put("lrt_score", 0);
        leng_map.put("lrt_pred", 0);
        leng_map.put("mutationtaster_score", 0);
        leng_map.put("mutationtaster_pred", 0);
        leng_map.put("mutationassessor_score", 0);
        leng_map.put("mutationassessor_pred", 0);
        leng_map.put("fathmm_score", 0);
        leng_map.put("fathmm_pred", 0);
        leng_map.put("provean_score", 0);
        leng_map.put("provean_pred", 0);
        leng_map.put("vest3_score", 0);
        leng_map.put("cadd_raw", 0);
        leng_map.put("cadd_phred", 0);
        leng_map.put("dann_score", 0);
        leng_map.put("fathmm_mkl_coding_score", 0);
        leng_map.put("fathmm_mkl_coding_pred", 0);
        leng_map.put("metasvm_score", 0);
        leng_map.put("metasvm_pred", 0);
        leng_map.put("metalr_score", 0);
        leng_map.put("metalr_pred", 0);
        leng_map.put("integrated_fitcons_score", 0);
        leng_map.put("integrated_confidence_value", 0);
        leng_map.put("gerp_rs", 0);
        leng_map.put("phylop7way_vertebrate", 0);
        leng_map.put("phylop20way_mammalian", 0);
        leng_map.put("phastcons7way_vertebrate", 0);
        leng_map.put("phastcons20way_mammalian", 0);
        leng_map.put("siphy_29way_logodds", 0);
        leng_map.put("baseqranksum", 0);
        leng_map.put("clippingranksum", 0);
        leng_map.put("mqranksum", 0);
        leng_map.put("readposranksum", 0);
    }

    @Autowired
    private SnpService snpService;
    @Autowired
    private SickFormatTask sickFormatTask;

    public void parse(String fileName, Long sickId) throws Exception {
        File f = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String str = null;
        // 读取数据行标记
        boolean flag = false;
        String[] dynamicHeader = null;
        while ((str = reader.readLine()) != null) {
            if (StringUtils.isEmpty(str)) {
                continue;
            } else if (str.startsWith(BEGIN)) {
                // 解析表头
                // 去掉开始#
                str = str.replace("#", "");
                if (!checkHeader(str)) {
                    String fixedColsStr = StringUtils.join(fixed_cols, TAB);
                    String msg = MessageFormat.format("VCF固定标题不匹配，该文件标题为：{0}，固定格式标题为：{1}", str, fixedColsStr);
                    throw new RRException(msg);
                }
                dynamicHeader = parseDynamicRow(str);
                flag = true;
                continue;
            }
            if (flag) {
                SnpEntity snpEntity = parseLine(str, getGeneType(), sickId);
                if (snpEntity == null) {
                    log.error("snpEntity is null...");
                    continue;
                }
                String[] dynamicRow = parseDynamicRow(str);
                List<SnpFormatEntity> formatList = parseFormatList(dynamicHeader, dynamicRow);
                sickFormatTask.parserMutation(formatList);
                SnpInfoEntity infoEntity;
                try {
                    infoEntity = parseInfo(snpEntity.getInfo());
                } catch (Exception e) {
                    log.error("解析Info出错，{}", e);
                    continue;
                }
                snpService.save(snpEntity, infoEntity, formatList);
            }
        }
        log.info("length_map : {}", leng_map);
    }

    private SnpEntity parseLine(String line, String dataType, Long sickId) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        String[] cols = StringUtils.split(line, TAB);
        log.info("title: {}", line);

        SnpEntity entity = null;
        try {
            entity = new SnpEntity(cols, dataType, sickId);
        } catch (Exception e) {
            log.error("数据行不合法： {}", line);
            e.getMessage();
            return null;
        }
        return entity;
    }

    private boolean checkHeader(String line) {
        String[] headers = line.split(TAB);
        int size = fixed_cols.length;
        String[] vcfFixedHeaders = new String[size];
        System.arraycopy(headers, 0, vcfFixedHeaders, 0, size);
        for (int i = 0; i < size; i++) {
            if (!fixed_cols[i].equals(vcfFixedHeaders[i])) {
                return false;
            }
        }
        return true;
    }

    private SnpInfoEntity parseInfo(String info) throws InvocationTargetException, IllegalAccessException {
        SnpInfoEntity entity = new SnpInfoEntity();
        String[] colsArray = StringUtils.split(info, INFO_SIGN);
        for (String str : colsArray) {
            String[] kv = StringUtils.split(str, EQUAL_SIGN);
            if (kv == null || kv.length != 2) {
                continue;
            }
            String key = kv[0].toLowerCase();
            String mappingKey = cols_map.get(key);
            if (!StringUtils.isEmpty(mappingKey)) {
                key = mappingKey;
            }
            String val = kv[1];
            //************
            if (StringUtils.isNotEmpty(val)) {
                int len = val.length();
                Integer oLen = leng_map.get(key);
                if (oLen == null) {
                    leng_map.put(key, 0);
                    log.error("key = {}, 未录入", key);
                    continue;
                }
                if (len > oLen.intValue()) {
                    leng_map.put(key, len);
                }
            }
            //************
            BeanUtils.setProperty(entity, key, val);
        }

        if (StringUtils.isNotBlank(entity.getPopfreqmax()) && EMPTY_VALUE.equals(entity.getPopfreqmax())) {
            entity.setPopfreqmax("0");
        }
        if (StringUtils.isNotBlank(entity.getExac_all()) && EMPTY_VALUE.equals(entity.getExac_all())) {
            entity.setExac_all("0");
        }
        if (StringUtils.isNotBlank(entity.getA1000g_all()) && EMPTY_VALUE.equals(entity.getA1000g_all())) {
            entity.setA1000g_all("0");
        }
        if (StringUtils.isNotBlank(entity.getExac_eas()) && EMPTY_VALUE.equals(entity.getExac_eas())) {
            entity.setExac_eas("0");
        }
        return entity;
    }

    private String[] parseDynamicRow(String line) {
        String[] headers = line.split(TAB);
        int dynamicSize = headers.length - fixed_cols.length;
        String[] vcfDynamicHeaders = new String[dynamicSize];
        System.arraycopy(headers, fixed_cols.length, vcfDynamicHeaders, 0, dynamicSize);
        return vcfDynamicHeaders;
    }

    private List<SnpFormatEntity> parseFormatList(String[] dynamicHeader, String[] dynamicRow) {
        List<SnpFormatEntity> list = new ArrayList<>();
        if (dynamicHeader == null || dynamicHeader.length < 1) {
            return list;
        }
        int sickNum = dynamicHeader.length - 1;
        for (int i = 0; i < sickNum; i++) {
            SnpFormatEntity entity = new SnpFormatEntity();
            entity.setSick(dynamicHeader[i + 1]);
            entity.setFormat(dynamicRow[0]);
            entity.setFormatVal(dynamicRow[i + 1]);
            entity.setFormatRate(dynamicRow[i + 1]);
            list.add(entity);
        }
        return list;
    }

    public abstract String getGeneType();

}
