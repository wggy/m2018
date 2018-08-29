package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import sw.melody.common.utils.Constant;

/***
 * Created by ping on 2018/6/20
 */
@Slf4j
@Component("geneSnpTask")
public class GeneSnpTask extends GeneAbsTask{

    public void parseSnp(String filename, Long sickId) throws Exception {
        super.parse(filename, sickId);
    }

    @Override
    public String getGeneType() {
        return Constant.DataType.Snp.name();
    }

    public static void main(String[] args) {
        String sql = "CREATE TABLE `NewTable` (\n" +
                "`id`  int NOT NULL AUTO_INCREMENT ,";
        String endSql = "PRIMARY KEY (`id`))\n;";

        String col = "AC=2;AF=1.00;AN=2;DP=7;ExcessHet=3.0103;FS=0.000;MLEAC=2;MLEAF=1.00;MQ=60.00;QD=28.40;SOR=0.941;ANNOVAR_DATE=2017-07-17;Func.refGene=upstream;Gene.refGene=RNR1;GeneDetail.refGene=dist\\x3d500;ExonicFunc.refGene=.;AAChange.refGene=.;Xref.refGene=.;cytoBand=.;avsnp147=.;CLINSIG=.;CLNDBN=.;CLNACC=.;CLNDSDB=.;CLNDSDBID=.;PopFreqMax=.;PopFreqMax=.;1000G_ALL=.;1000G_AFR=.;1000G_AMR=.;1000G_EAS=.;1000G_EUR=.;1000G_SAS=.;ExAC_ALL=.;ExAC_AFR=.;ExAC_AMR=.;ExAC_EAS=.;ExAC_FIN=.;ExAC_NFE=.;ExAC_OTH=.;ExAC_SAS=.;ESP6500siv2_ALL=.;ESP6500siv2_AA=.;ESP6500siv2_EA=.;CG46=.;SIFT_score=.;SIFT_pred=.;Polyphen2_HDIV_score=.;Polyphen2_HDIV_pred=.;Polyphen2_HVAR_score=.;Polyphen2_HVAR_pred=.;LRT_score=.;LRT_pred=.;MutationTaster_score=.;MutationTaster_pred=.;MutationAssessor_score=.;MutationAssessor_pred=.;FATHMM_score=.;FATHMM_pred=.;PROVEAN_score=.;PROVEAN_pred=.;VEST3_score=.;CADD_raw=.;CADD_phred=.;DANN_score=.;fathmm-MKL_coding_score=.;fathmm-MKL_coding_pred=.;MetaSVM_score=.;MetaSVM_pred=.;MetaLR_score=.;MetaLR_pred=.;integrated_fitCons_score=.;integrated_confidence_value=.;GERP++_RS=.;phyloP7way_vertebrate=.;phyloP20way_mammalian=.;phastCons7way_vertebrate=.;phastCons20way_mammalian=.;SiPhy_29way_logOdds=.;ALLELE_END";
        String[] colsArray = StringUtils.split(col, ";");
        String obj = "";
        String insert = "";
        for (String str : colsArray) {
            String[] kv = StringUtils.split(str, "=");
            if (kv == null || kv.length != 2) {
                continue;
            }
        }
        System.out.println(insert);
    }
}
