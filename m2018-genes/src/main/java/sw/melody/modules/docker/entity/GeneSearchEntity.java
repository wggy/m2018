package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ping
 * @create 2018-08-09 10:59
 **/
@Setter
@Getter
public class GeneSearchEntity {

    // 基因

    private String geneRefgene;

    // Hpo：留空

    private String hpo;

    // 突变信息，由四部分组成，AAChange.refGene（c.T260C），CHROM-POS，AAChange.refGene（p.I87T），ExonicFunc.refGene

    private String aachangeRefgene;
    private String chromPos;
    private String exonicfuncrefgene;
    private String mutationInfo;

    // HGMD数据库：留空

    private String hgmd;

    // Clinical_variant：留空

    private String clinicalVariant;

    // 个人专属库：留空

    private String personalDb;

    // 突变位点质量：留空

    private String mutationQuatity;

    // 疾病信息：Xref.refGene（Xref.refGene=Muscular_dystrophy-dystroglycanopathy_(congenital_with_brain_and_eye_anomalies),_type_A,_3,_Autosomal_recessive\x3bMuscular_dystrophy-dystroglycanopathy_(congenital_with_mental_retardation),_type_B,_3,_Autosomal_recessive\x3bMuscular_dystrophy-dystroglycanopathy_(limb-girdle),_type_C,_3,_Autosomal_recessive\x3bRetinitis_pigmentosa_76,_Autosomal_recessive;）

    private String xrefRefgene;

    // 遗传方式：取自疾病信息

    private String hereditaryMode;

    // HPO/代谢数据库：留空

    private String hpoMetabolizeDb;

    // 主要致病突变类型：留空

    private String mainMutationMode;

    // 患者姓名

    private String sampleName;

    /**
     Allele frequency： 包含六行属性
     第一行：PopFreqMax，有两个，取第一个即可
     第二行：1000G_ALL
     第三行：1000G_EAS
     第四行：ExAC_ALL
     第五行：ExAC_EAS
     第六行：ESP6500siv2_ALL
     */

    private String popfreqmax;
    private String a1000gAll;
    private String a1000gEas;
    private String exacAll;
    private String exacEas;
    private String esp6500siv2All;
    private String alleleFrequency;


    // Revel/M-CAP 错义突变危害性评估 ada_score rf_score ：留空

    private String revelMcap;

    // SIFT/Polyphen 错义突变危害性评估

    private String siftPolyphen;

    // 单点分数

    private String singlePointScore;

    // Bp Score

    private String bpScore;


    public static void parseAttr(GeneSearchEntity entity) {

    }
}
