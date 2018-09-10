package sw.melody.nio;

import java.io.*;

/**
 * @author ping
 * @create 2018-09-07 15:02
 **/

public class NioCopyFile {
    public static void main(String[] args) throws Exception {
        mergeFile(4638);
//        splitDemo();
    }


    public static void splitDemo() throws IOException {
        FileInputStream fis = new FileInputStream("E:\\docker_cdh\\cdh-docker-img\\cdh-5.7.0.tar");
        FileOutputStream fos;
        byte[] buf = new byte[1024 * 1024];
        int len, count = 0;

        while ((len = fis.read(buf)) != -1) {
            System.out.println(count);
            fos = new FileOutputStream("E:\\docker_cdh\\cdh-docker-img\\split\\" + (count++) + ".tar");
            fos.write(buf, 0, len);
            fos.flush();
            fos.close();
        }
        fis.close();
    }


    public static void mergeFile(int chunksNumber)
            throws Exception {
        long start = System.currentTimeMillis();
        String ext = ".tar";
        String mergePath = "E:\\docker_cdh\\cdh-docker-img\\split\\";
        SequenceInputStream s ;
        InputStream s1 = new FileInputStream(mergePath + 0 + ext);
        InputStream s2 = new FileInputStream(mergePath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(mergePath + i + ext);
            s = new SequenceInputStream(s, s3);
        }
        long m1 = System.currentTimeMillis();

        System.out.println("add files: " + (m1 - start));

        //通过输出流向文件写入数据
        String writeToPath = mergePath;
        if (mergePath.endsWith(File.separator)) {
            writeToPath = mergePath.substring(0, mergePath.length()-1);
        }
        saveStreamToFile(s, writeToPath + ext);
        long m2 = System.currentTimeMillis();
        System.out.println("merge files: " + (m2 - m1));

        long m3 = System.currentTimeMillis();

        //删除保存分块文件的文件夹
        deleteFolder(mergePath);
        System.out.println("delete files: " + (m3 - m2));


    }

    public static void saveStreamToFile(InputStream inputStream, String filePath)
            throws Exception {
         /*创建输出流，写入数据，合并分块*/
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    public static boolean deleteFolder(String folderPath) {
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        if(files!=null){
            for (File file : files) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dir.delete();
    }
}
