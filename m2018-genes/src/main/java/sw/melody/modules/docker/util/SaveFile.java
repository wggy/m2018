package sw.melody.modules.docker.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class SaveFile {

    private static final File uploadDirectory = new File(getRealPath());


    /**
     * @param savePath
     * @param fileFullName
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean saveFile(@NotNull final String savePath,
                                   @NotNull final String fileFullName,
                                   @NotNull final MultipartFile file)
            throws Exception {
        byte[] data = readInputStream(file.getInputStream());
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File uploadFile = new File(savePath + fileFullName);
        //判断文件夹是否存在，不存在就创建一个
        File fileDirectory = new File(savePath);
        synchronized (uploadDirectory) {
            if (!uploadDirectory.exists()) {
                if (!uploadDirectory.mkdir()) {
                    throw new Exception("保存文件的父文件夹创建失败！路径为：" + savePath);
                }
            }
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new Exception("文件夹创建失败！路径为：" + savePath);
                }
            }
        }

        //创建输出流
        try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {
            outStream.write(data);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return uploadFile.exists();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 获得绝对路径（不带文件名）
     *
     * @return
     */
    public static String getRealPath() {
        return "/home/lgf";
    }


    /**
     * 根据文件路径获取File
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File getFileByPath(String filePath) throws IOException {
        Path path = Paths.get(getRealPath() + filePath);
        if (Files.exists(path)) {
            return new File(path.toUri());
        }
        return null;
    }

    /**
     * 压缩文件
     *
     * @param srcFileList
     * @param zipFile
     * @throws IOException
     */
    public static void zipFiles(List<File> srcFileList, File zipFile) throws IOException {
        byte[] buf = new byte[1024];
        //ZipOutputStream类：完成文件或文件夹的压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        for (File aSrcFileList : srcFileList) {
            FileInputStream in = new FileInputStream(aSrcFileList);
            out.putNextEntry(new ZipEntry(aSrcFileList.getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
    }
}
