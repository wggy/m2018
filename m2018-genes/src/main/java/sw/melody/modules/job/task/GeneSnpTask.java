package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sw.melody.common.exception.RRException;
import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.service.SnpFormatService;
import sw.melody.modules.job.service.SnpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018/6/20
 */
@Slf4j
@Component("geneSnpTask")
public class GeneSnpTask {
    // 表头开始标记
    private static final String BEGIN = "#CHROM";
    private static final String TAB = "\t";
    private static final String LF = "\n";
    // 固定表头
    private static String[] fixed_cols = new String[]{"CHROM", "POS", "ID", "REF", "ALT", "QUAL", "FILTER", "INFO"};
    private static final Map<String, String> cols_map = new HashMap<>();

    static {
        cols_map.put("func.refgene", "func_refgene");
        cols_map.put("gene.refgene", "gene_refgene");
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
    }

    @Autowired
    private SnpService snpService;
    @Autowired
    private SnpFormatService snpFormatService;

    public void parseSnp(String fileName) throws Exception {
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
                SnpEntity snpEntity = parseLine(str);
                String[] dynamicRow = parseDynamicRow(str);
                List<SnpFormatEntity> formatList = parseFormatList(dynamicHeader, dynamicRow);
                snpService.save(snpEntity, formatList);
            }
        }
    }

    private SnpEntity parseLine(String line) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        String[] cols = StringUtils.split(line, TAB);
        SnpEntity entity = new SnpEntity(cols);
        return entity;
    }

    private boolean checkHeader(String line) {
        String[] headers = line.split(TAB);
        int size = fixed_cols.length;
        String[] vcfFixedHeaders = new String[size];
        System.arraycopy(headers, 0, vcfFixedHeaders, 0, size);
        for (int i=0; i<size; i++) {
            if (!fixed_cols[i].equals(vcfFixedHeaders[i])) {
                return false;
            }
        }
        return true;
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
        for (int i=0; i<sickNum; i++) {
            SnpFormatEntity entity = new SnpFormatEntity();
            entity.setSick(dynamicHeader[i+1]);
            entity.setFormat(dynamicRow[0]);
            entity.setFormatVal(dynamicRow[i+1]);
            entity.setFormatRate(dynamicRow[i+1]);
            list.add(entity);
        }
        return list;
    }

    public static void main(String[] args) {
        String sql = "CREATE TABLE `NewTable` (\n" +
                "`id`  int NOT NULL AUTO_INCREMENT ,";
        String endSql = "PRIMARY KEY (`id`))\n;";

        String col = "AC=2;AF=1.00;AN=2;DP=7;ExcessHet=3.0103;FS=0.000;MLEAC=2;MLEAF=1.00;MQ=60.00;QD=28.40;SOR=0.941;ANNOVAR_DATE=2017-07-17;Func.refGene=upstream;Gene.refGene=RNR1;GeneDetail.refGene=dist\\x3d500;ExonicFunc.refGene=.;AAChange.refGene=.;Xref.refGene=.;cytoBand=.;avsnp147=.;CLINSIG=.;CLNDBN=.;CLNACC=.;CLNDSDB=.;CLNDSDBID=.;PopFreqMax=.;PopFreqMax=.;1000G_ALL=.;1000G_AFR=.;1000G_AMR=.;1000G_EAS=.;1000G_EUR=.;1000G_SAS=.;ExAC_ALL=.;ExAC_AFR=.;ExAC_AMR=.;ExAC_EAS=.;ExAC_FIN=.;ExAC_NFE=.;ExAC_OTH=.;ExAC_SAS=.;ESP6500siv2_ALL=.;ESP6500siv2_AA=.;ESP6500siv2_EA=.;CG46=.;SIFT_score=.;SIFT_pred=.;Polyphen2_HDIV_score=.;Polyphen2_HDIV_pred=.;Polyphen2_HVAR_score=.;Polyphen2_HVAR_pred=.;LRT_score=.;LRT_pred=.;MutationTaster_score=.;MutationTaster_pred=.;MutationAssessor_score=.;MutationAssessor_pred=.;FATHMM_score=.;FATHMM_pred=.;PROVEAN_score=.;PROVEAN_pred=.;VEST3_score=.;CADD_raw=.;CADD_phred=.;DANN_score=.;fathmm-MKL_coding_score=.;fathmm-MKL_coding_pred=.;MetaSVM_score=.;MetaSVM_pred=.;MetaLR_score=.;MetaLR_pred=.;integrated_fitCons_score=.;integrated_confidence_value=.;GERP++_RS=.;phyloP7way_vertebrate=.;phyloP20way_mammalian=.;phastCons7way_vertebrate=.;phastCons20way_mammalian=.;SiPhy_29way_logOdds=.;ALLELE_END";
        String[] colsArray = StringUtils.split(col, ";");
        String obj = "";
        for (String str : colsArray) {
            String[] kv = StringUtils.split(str, "=");
            if (kv == null || kv.length != 2) {
                continue;
            }
            String key = kv[0].toLowerCase();
//            String val = kv[1];
            String colSql = MessageFormat.format("`{0}` varchar(50) NULL, \n", key);
            sql += colSql;
            obj += MessageFormat.format("private String {0};\n", key);
        }
//        System.out.println(sql + endSql);
        System.out.println(obj);
    }

}
