package com.yzm.majorserver.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 14:57 2019/5/30
 * ===========================
 */
public class Base64Util {
    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * 解密
     *
     * @param s
     * @return
     */
    public static String getFromBase64(String s) {
        byte[] b;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
