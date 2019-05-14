package com.wggy.prune.common;

import java.util.HashMap;

/**
 * @author ping
 * @create 2019-04-01 17:48
 **/

public class Rr extends HashMap<String, Object> {

    public Rr(RrPair pair) {
        this.put("code", pair.code);
        this.put("msg", pair.msg);
    }

    public Rr(int code, String msg) {
        this.put("code", code);
        this.put("msg", msg);
    }


    public static Rr ok() {
        return new Rr(RrPair.SUCCESS);
    }

    public static Rr fail(String msg) {
        return new Rr(RrPair.FAIL.code, msg);
    }

    enum RrPair {
        SUCCESS(0, "success"),
        FAIL(999, "fail");

        int code;
        String msg;

        RrPair(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
