package sw.melody.modules.docker.util;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ping
 * @create 2018-09-04 15:14
 **/

public class MoreLogUtil {

    private static final Logger log = LoggerFactory.getLogger(MoreLogUtil.class);
    private static final ConcurrentHashMap<String, RandomAccessFile> RafRepo = new ConcurrentHashMap<>();

    public synchronized static RandomAccessFile getRaf(String logFileName) throws FileNotFoundException {
        RandomAccessFile raf = RafRepo.get(logFileName);
        if (raf == null) {
            raf = new RandomAccessFile(new File(logFileName), "r");
            RafRepo.put(logFileName, raf);
            return raf;
        } else {
            return raf;
        }
    }

    public static LogResult readLog(String logFileName, int fromLineNum) {
        if (logFileName == null || logFileName.trim().length() == 0) {
            return new LogResult(fromLineNum, 0, "readLog fail, logFile not found", true);
        }
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            return new LogResult(fromLineNum, 0, "readLog fail, logFile not exists", true);
        }

        StringBuffer logContentBuffer = new StringBuffer();
        int toLineNum = 0;
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(logFile), "utf-8"));
            String line;

            while ((line = reader.readLine()) != null) {
                toLineNum = reader.getLineNumber();
                if (toLineNum >= fromLineNum) {
                    logContentBuffer.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LogResult logResult = new LogResult(fromLineNum, toLineNum, logContentBuffer.toString(), false);
        return logResult;
    }

    public static String getLastLine(String logFileName, String charset) {
        if (logFileName == null || logFileName.trim().length() == 0) {
            log.error("文件不能为空");
            return null;
        }
        File file = new File(logFileName);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            log.error("{} 文件不存在或是目录或不可读", logFileName);
            return null;
        }
        try {
            RandomAccessFile raf = getRaf(logFileName);
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLastLine(String logFileName) {
        return getLastLine(logFileName, null);
    }

    public static void closeRaf(String logName) {
        RandomAccessFile file = RafRepo.get(logName);
        if (file != null) {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RafRepo.remove(logName);
        }
    }

    @Setter
    @Getter
    public static class LogResult {
        private int fromLineNum;
        private int toLineNum;
        private String logContent;
        private boolean isEnd;

        public LogResult(int fromLineNum, int toLineNum, String logContent, boolean isEnd) {
            this.fromLineNum = fromLineNum;
            this.toLineNum = toLineNum;
            this.logContent = logContent;
            this.isEnd = isEnd;
        }
    }

    public static void main(String[] args) {
//        String line = getLastLine("E:\\funnel.txt", null);
        File file = new File("E:\\data\\logs\\nubia-shop-show\\nubia-shop-show.log");
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
    }

}
