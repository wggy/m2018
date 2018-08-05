package sw.melody.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * 系统参数相关Key
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 10:33
 */
public class ConfigConstant {
    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /***
     * 文件上传路径
     */
    public static final String UPLOAD_FILE_PREFIX = "UPLOAD_FILE_PREFIX";

    /***
     * 样本解析脚本路径
     */
    public static final String SAMPLE_SHELL_PATH = "SAMPLE_SHELL_PATH";

    /***
     * 文件分隔符
     */
    public static final String File_Separator = File.separator;

    /***
     * 可上传的文件
     */
    public static final String File_Allowed = "FILE_ALLOWED";
    public static final String Shell_Bwa = "run_bwa.sh";

    public static final String Result_Indel_File_Prefix = "_sorted_dedup_recal_raw_variants_indel_marker_filtered_annovar.hg19_multianno.vcf";
    public static final String Result_Snp_File_Prefix   = "_sorted_dedup_recal_raw_variants_snp_marked_filtered_annovar.hg19_multianno.vcf";

    /***
     * 上传文件短路径
     * @param sickCode ： 病患编码
     * @param suffix ：文件拓展名
     * @return
     */
    public static String getShortPath(String sickCode, String suffix) {
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + File_Separator + sickCode;
        return path + "." + suffix;
    }

    public static String getFullPathNoFile(String prefix) {
        return prefix + File_Separator + DateUtils.format(new Date(), "yyyyMMdd");
    }

    /***
     * 上传文件全路径
     * @param prefix ： 开始路径
     * @param sickCode ： 病患编码
     * @param suffix ： 文件后缀
     * @return
     */
    public static String getFullPath(String prefix, String sickCode, String suffix) {
        String path = getShortPath(sickCode, suffix);
        if(StringUtils.isNotBlank(prefix)){
            path = prefix + File_Separator + path;
        }
        return path + suffix;
    }

    /***
     * 上传文件全路径
     * @param prefix ： 开始路径
     * @param shortPath ： 文件短路径
     * @return
     */
    public static String getFullPath(String prefix, String shortPath) {
        return prefix + File_Separator + shortPath;
    }
}
