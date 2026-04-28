package com.bytedance.douyinclouddemo.common.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignUtil {

    public static String sha1(String token, String timestamp, String nonce, String msg) throws Exception {

        String[] arr = new String[]{token, timestamp, nonce, msg};
        Arrays.sort(arr); // 字符串自然排序

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md.digest(sb.toString().getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }

}
