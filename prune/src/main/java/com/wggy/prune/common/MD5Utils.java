package com.wggy.prune.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;

/**
 * @author ping
 * @create 2019-04-02 16:01
 **/

public class MD5Utils {

    private static final String hexDigIts[] = {"j", "u", "n", "m", "u", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String MD5Encode(String origin) {
        return mD5Encode(origin, "utf8");
    }

    public static String mD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isEmpty(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis() /1000;
        System.out.println(time);


        String signVal = DigestUtils.sha1Hex("appKey=00001date=2019-01-01time=1557730506ccd10fc388df4447b052e8103f0108c7");
        System.out.println(signVal);
    }
}
