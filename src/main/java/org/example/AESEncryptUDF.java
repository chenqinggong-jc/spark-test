package org.example;


import org.apache.hadoop.hive.ql.exec.UDF;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class AESEncryptUDF extends UDF {

    public String evaluate(String plaintext, String key) {
        if (plaintext == null || key == null) {
            return null;
        }

        try {
            // AES默认的块大小是128位，所以key也应该是128位（16字节）
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            // 使用Base64编码以确保输出可以作为文本存储
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage());
        }
    }
}

