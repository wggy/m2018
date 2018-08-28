package sw.melody.modules.docker.util;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;

/**
 * @author wange
 */
public class MergeFile {

    /**
     * @param chunksNumber
     * @param ext
     * @param guid
     * @param uploadFolderPath
     * @throws Exception
     */
    public static void mergeFile(final int chunksNumber,
                                 @NotNull final String ext,
                                 @NotNull final String guid,
                                 @NotNull final String uploadFolderPath)
            throws Exception {
        /*合并输入流*/
        String mergePath = addFileSeparator(uploadFolderPath);
        SequenceInputStream s ;
        InputStream s1 = new FileInputStream(mergePath + 0 + ext);
        InputStream s2 = new FileInputStream(mergePath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(mergePath + i + ext);
            s = new SequenceInputStream(s, s3);
        }

        //通过输出流向文件写入数据
        String writeToPath = uploadFolderPath;
        if (uploadFolderPath.endsWith(ConfigConstant.File_Separator)) {
            writeToPath = uploadFolderPath.substring(0, uploadFolderPath.length()-1);
        }
        StreamUtil.saveStreamToFile(s, writeToPath + ext);

        //删除保存分块文件的文件夹
        DeleteFolder.deleteFolder(mergePath);

    }

    public static String addFileSeparator(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RRException("路径为空");
        }
        if (path.endsWith(ConfigConstant.File_Separator)) {
            return path;
        }
        return path + ConfigConstant.File_Separator;
    }
}
