package sw.melody.config;

/***
 * Created by ping on 2018/2/26
 * 系统的顶级异常
 */
public class GlobalException extends RuntimeException {
    public static final int ErrorCode = 999;
    public static final int SuccssCode = 0;

    private int code;
    private String url;
    private String message;
    private String data;

    public GlobalException(int code, String url, String message, String data) {
        this.code = code;
        this.url = url;
        this.message = message;
        this.data = data;
    }

    public GlobalException(int code, String message) {
        this(code, null, message, null);
    }

    public GlobalException(String message) {
        this(ErrorCode, null, message, null);
    }

    public GlobalException() {
        this(SuccssCode, null, "success", null);
    }
}
