package sw.melody.modules.docker.util;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author wange
 */
public class MergeFile {

    private static final Logger logger = LoggerFactory.getLogger(MergeFile.class);
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
        long start = System.currentTimeMillis();
        logger.info("{}: 文件‘{}’开始合并", Thread.currentThread().getName(), guid);
        String mergePath = addFileSeparator(uploadFolderPath);
        SequenceInputStream s ;
        InputStream s1 = new FileInputStream(mergePath + 0 + ext);
        InputStream s2 = new FileInputStream(mergePath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(mergePath + i + ext);
            s = new SequenceInputStream(s, s3);
        }
        logger.info("{}: 文件‘{}’SequenceInputStream小文件流加载成功，开始写入大文件", Thread.currentThread().getName(), guid);
        //通过输出流向文件写入数据
        String writeToPath = uploadFolderPath;
        if (uploadFolderPath.endsWith(ConfigConstant.File_Separator)) {
            writeToPath = uploadFolderPath.substring(0, uploadFolderPath.length()-1);
        }
        StreamUtil.saveStreamToFile(s, writeToPath + ext);

        logger.info("{}: 文件‘{}’大文件合并成功，开始删除中间小文件", Thread.currentThread().getName(), guid);
        //删除保存分块文件的文件夹
        DeleteFolder.deleteFolder(mergePath);
        logger.info("{}: 文件‘{}’删除中间文件成功", Thread.currentThread().getName(), guid);
        long end = System.currentTimeMillis();
        logger.info("{}: 合并文件完成，耗时：{}毫秒", Thread.currentThread().getName(), (end - start));
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


    public static void mergeFileByNio(final int chunksNumber,
                                 @NotNull final String ext,
                                 @NotNull final String guid,
                                 @NotNull final String uploadFolderPath)
            throws Exception {
        long start = System.currentTimeMillis();
        logger.info("{}: 文件‘{}’开始合并", Thread.currentThread().getName(), guid);
        String mergePath = addFileSeparator(uploadFolderPath);

        //通过输出流向文件写入数据
        String writeToPath = uploadFolderPath;
        if (uploadFolderPath.endsWith(ConfigConstant.File_Separator)) {
            writeToPath = uploadFolderPath.substring(0, uploadFolderPath.length()-1);
        }
        logger.info("{}: 文件‘{}’开始持续写入大文件", Thread.currentThread().getName(), guid);

        FileChannel fcOut = new FileOutputStream(writeToPath + ext, true).getChannel();
        for (int i = 0; i < chunksNumber; i++) {
            FileChannel fcIn = new FileInputStream(mergePath + i + ext).getChannel();
            fcOut.transferFrom(fcIn, fcOut.size(), fcIn.size());
            fcIn.close();
        }
        fcOut.close();
        logger.info("{}: 文件‘{}’大文件合并成功，开始删除中间小文件", Thread.currentThread().getName(), guid);
        //删除保存分块文件的文件夹
        DeleteFolder.deleteFolder(mergePath);
        logger.info("{}: 文件‘{}’删除中间文件成功", Thread.currentThread().getName(), guid);
        long end = System.currentTimeMillis();
        logger.info("{}: 合并文件完成，耗时：{}毫秒", Thread.currentThread().getName(), (end - start));
    }
}
