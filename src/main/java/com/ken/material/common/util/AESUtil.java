package com.ken.material.common.util;

import org.apache.commons.io.Charsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-21
 */
public class AESUtil {

    public static final String KEY = "0ppS5HR1Q55EDpX0yGhndg==";
    public static final String iv = "9H/L/IvIaBSHd46FAVZiEA==";

    public static String decryptAES(String data) {
        return decryptAES(data, KEY, iv);
    }

    public static String encryptAES(String data) {
        return encryptAES(data, KEY, iv);
    }

    public static String decryptAES(String data, String key, String iv) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        byte[] decodeData = Base64.getDecoder().decode(data);

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(2, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(decodeData);
            String result = new String(bytes, Charsets.UTF_8);
            return result.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptAES(String data, String key, String iv) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            byte[] dataBytes = data.getBytes(Charsets.UTF_8);
            int plaintextLength = dataBytes.length;
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            int blockSize = cipher.getBlockSize();
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(1, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(plaintext);
            String result = Base64.getEncoder().encodeToString(bytes);
            return result.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
