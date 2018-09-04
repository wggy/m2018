package sw.melody.modules.docker.util;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
 * @author ping
 * @create 2018-09-04 15:14
 **/

public class MoreLogUtil {

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


}
