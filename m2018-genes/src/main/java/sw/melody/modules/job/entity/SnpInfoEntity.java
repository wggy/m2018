package sw.melody.modules.job.entity;

import lombok.Getter;
import lombok.Setter;

/***
 * Created by ping on 2018-7-2
 */
@Setter
@Getter
public class SnpInfoEntity {
    private Integer id;
    private String ac;
    private String af;
    private String an;
    private String dp;
    private String excesshet;
    private String fs;
    private String mleac;
    private String mleaf;
    private String mq;
    private String qd;
    private String sor;
    private String annovar_date;
    private String func_refgene;
    private String gene_refgene;
    private String genedetail_refgene;
    private String exonicfunc_refgene;
    private String aachange_refgene;
    private String xref_refgene;
    private String cytoband;
    private String avsnp147;
    private String clinsig;
    private String clndbn;
    private String clnacc;
    private String clndsdb;
    private String clndsdbid;
    private String popfreqmax;
    private String a1000g_all;
    private String a1000g_afr;
    private String a1000g_amr;
    private String a1000g_eas;
    private String a1000g_eur;
    private String a1000g_sas;
    private String exac_all;
    private String exac_afr;
    private String exac_amr;
    private String exac_eas;
    private String exac_fin;
    private String exac_nfe;
    private String exac_oth;
    private String exac_sas;
    private String esp6500siv2_all;
    private String esp6500siv2_aa;
    private String esp6500siv2_ea;
    private String cg46;
    private String sift_score;
    private String sift_pred;
    private String polyphen2_hdiv_score;
    private String polyphen2_hdiv_pred;
    private String polyphen2_hvar_score;
    private String polyphen2_hvar_pred;
    private String lrt_score;
    private String lrt_pred;
    private String mutationtaster_score;
    private String mutationtaster_pred;
    private String mutationassessor_score;
    private String mutationassessor_pred;
    private String fathmm_score;
    private String fathmm_pred;
    private String provean_score;
    private String provean_pred;
    private String vest3_score;
    private String cadd_raw;
    private String cadd_phred;
    private String dann_score;
    private String fathmm_mkl_coding_score;
    private String fathmm_mkl_coding_pred;
    private String metasvm_score;
    private String metasvm_pred;
    private String metalr_score;
    private String metalr_pred;
    private String integrated_fitcons_score;
    private String integrated_confidence_value;
    private String gerp_rs;
    private String phylop7way_vertebrate;
    private String phylop20way_mammalian;
    private String phastcons7way_vertebrate;
    private String phastcons20way_mammalian;
    private String siphy_29way_logodds;
}
