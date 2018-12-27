package sw.melody.modules.docker.entity;

import com.aliyun.oss.model.OSSObjectSummary;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author ping
 * @create 2018-12-24 17:06
 **/
@Setter
@Getter
public class OSSFileEntity {

    private Long id;
    private Long parentId;
    private String fileName;
    private String path;
    private Long fileSize;
    private String fileSizeFmt;
    private String fileType;
    private String bucketName;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer level;


    public static OSSFileEntity toFile(OSSObjectSummary summary) {
        if (summary == null) {
            return null;
        }
        OSSFileEntity entity = new OSSFileEntity();
        String[] arrayKeys = StringUtils.splitPreserveAllTokens(summary.getKey(), "/");
        int length = arrayKeys.length;
        entity.setBucketName(summary.getBucketName());
        entity.setCreateTime(new Date());
        if ("".equals(arrayKeys[length - 1])) {
            entity.setFileName(arrayKeys[length - 2]);
            entity.setFileType("D");
            entity.setLevel(length - 2);
        } else {
            entity.setFileName(arrayKeys[length - 1]);
            entity.setFileType("F");
            entity.setLevel(length - 1);
            entity.setFileSizeFmt(getPrintSize(summary.getSize()));
        }
        if (entity.getLevel() == 0) {
            entity.setParentId(0L);
        }
        entity.setFileSize(summary.getSize());
        entity.setLastUpdateTime(summary.getLastModified());
        entity.setPath(summary.getKey());
        return entity;
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }
}
