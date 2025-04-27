package org.example;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5UDF extends UDF {
    public String evaluate(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
