package sw.melody.config;

import java.util.HashMap;

/***
 * Created by ping on 2018/3/1
 */
public class R extends HashMap<String, Object> {

    public static final int successCode = 0;
    public static final int defaultErrorCode = 999;

    public R(int code, String message, Object data) {
        put("code", successCode);
        put("message", message);
        put("data", data);
    }

    public R() {
        this(successCode, "success", null);
    }

    public static R ok() {
        return new R();
    }
    public static R ok(String message) {
        return new R(successCode, message, null);
    }
    public static R ok(Object data) {
        return new R(successCode, "success", data);
    }

    public static R error() {
        return new R(defaultErrorCode, "failed", null);
    }

    public static R error(String message) {
        return new R(defaultErrorCode, message, null);
    }
}
