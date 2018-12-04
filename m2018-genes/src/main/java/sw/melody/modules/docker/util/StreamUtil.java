package sw.melody.modules.docker.util;


import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
    /**
     * 从stream中保存文件
     *
     * @param inputStream inputStream
     * @param filePath    保存路径
     * @throws Exception 异常 抛异常代表失败了
     */
    public static void saveStreamToFile(@NotNull final InputStream inputStream,
                                        @NotNull final String filePath)
            throws Exception {
         /*创建输出流，写入数据，合并分块*/
        logger.info("{}: 文件‘{}’大文件流写入开始", Thread.currentThread().getName(), filePath);
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            logger.info("{}: 文件‘{}’大文件流写入成功", Thread.currentThread().getName(), filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }
}
