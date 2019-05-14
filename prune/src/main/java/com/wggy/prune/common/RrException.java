package com.wggy.prune.common;

import org.springframework.util.StringUtils;

/**
 * @author ping
 * @create 2019-04-02 15:48
 **/

public class RrException extends RuntimeException {

    public RrException(String message) {
        super(message);
    }

    public RrException() {

    }

    public static void exception(String msg) {
        throw new RrException(msg);
    }

    public static void nullException() {
        throw new RrException("请求参数或返回记录为空");
    }

    public static void nullException(Object obj) {
        if (obj == null) {
            nullException();
        }
    }

    public static void nullException(String... strings) {
        for (String item : strings) {
            if (StringUtils.isEmpty(item)) {
                nullException();
                break;
            }
        }
    }


}
