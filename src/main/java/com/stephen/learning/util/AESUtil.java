package com.stephen.learning.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author jack
 * @description
 * @date 2020/5/10 20:39
 */
public class AESUtil {
    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);


    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, new SecretKeySpec(key.getBytes(), "AES"));
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception var5) {
            log.error("AES encrypt error ", var5);
            return null;
        }
    }

    public static String decrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"));
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception var4) {
            log.error("AES decrypt error ", var4);
            return null;
        }
    }

    private static SecretKeySpec getSecretKey(final String key) {
        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException var3) {
            log.error("AES getSecretKey error ", var3);
            return null;
        }
    }

    public static String encryptCBC(String content, String key, String ivParameter) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(1, new SecretKeySpec(key.getBytes(), "AES"), ivParameterSpec);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception var7) {
            log.error("AES encrypt error ", var7);
            return null;
        }
    }

    public static String decryptCBC(String content, String key, String ivParameter) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), ivParameterSpec);
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception var6) {
            log.error("AES decrypt error ", var6);
            return null;
        }
    }

    public static void main(String[] args) {
        String context= "{\"name\":\"jack\",\"sex\":\"male\",\"email\":\"xy123zk@163.com\",\"age\":28}";
        String target = AESUtil.encryptCBC(context,"aes0123456789aes","0123456789abcdef");
        System.out.println(target);
    }
}
