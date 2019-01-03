package sw.melody.modules.docker.util;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.multipart.MultipartFile;
import sw.melody.common.exception.RRException;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author wange
 */
public class SaveFile implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SaveFile.class);
    protected Object lockObj = new Object();

    private static SysConfigService sysConfigService;

    /**
     * @param savePath
     * @param fileFullName
     * @param file
     * @return
     * @throws Exception
     */
    public boolean saveFile(@NotNull final String savePath,
                                   @NotNull final String fileFullName,
                                   @NotNull final MultipartFile file)
            throws Exception {
        byte[] data = readInputStream(file.getInputStream());
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File uploadFile = new File(addFileSeparator(savePath) + fileFullName);
        //判断文件夹是否存在，不存在就创建一个
        File fileDirectory = new File(savePath);

        synchronized (lockObj) {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
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

    public byte[] readInputStream(InputStream inStream) throws Exception {
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
    public String getRealPath() {
        String prefix = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
        return prefix;
    }

    public String addFileSeparator(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RRException("路径为空");
        }
        if (path.endsWith(ConfigConstant.File_Separator)) {
            return path;
        }
        return path + ConfigConstant.File_Separator;
    }

    public static String linkFileSeparator(String path) {
        if (StringUtils.isBlank(path)) {
            throw new RRException("路径为空");
        }
        if (path.endsWith(ConfigConstant.File_Separator)) {
            return path;
        }
        return path + ConfigConstant.File_Separator;
    }

    public static String callShellCommand(String fullPathNoFile, String targetFileName, String secFileName) {
        String nohupShell = sysConfigService.getValue(ConfigConstant.Shell_Bwa);
        if (StringUtils.isBlank(secFileName)) {
            return "cd " + fullPathNoFile + " &&  " + nohupShell + " " + targetFileName + " > " + targetFileName + ".out 2>&1 &";
        }
        return "cd " + fullPathNoFile + " &&  " + nohupShell  + " " + targetFileName + " " + secFileName + " > " + targetFileName + ".out 2>&1 &";
    }

    /**
     * 根据文件路径获取File
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public File getFileByPath(String filePath) throws IOException {
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sysConfigService = applicationContext.getBean(SysConfigService.class);
    }

    protected void triggerShell(String command) throws Exception {
        Process ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
        int status = ps.waitFor();
        log.info("status: {}", status);
        if (status != 0) {
            log.error("Failed to call shell's command ");
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                log.info(line);
            }
            br.close();
        }
    }
}
