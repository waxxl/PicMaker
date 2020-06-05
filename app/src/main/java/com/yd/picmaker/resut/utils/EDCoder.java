package com.yd.picmaker.resut.utils;

import android.util.Base64;

import com.yd.picmaker.resut.ExtResConfig;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EDCoder {
    //    public static byte key = 0x33;
    public static byte[] key = ExtResConfig.resKey; //128bit
    public static Key aesKey = getKey(key);
    public static String cipherName = "AES/ECB/PKCS5Padding";

    public static Key getKey(byte[] key) {
        try {
            Key key1 = new SecretKeySpec(key, "AES");
            return key1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encode(byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    public static byte[] decode(byte[] in, int s, int len) {
        Key secretKey = aesKey;
        try {
            Cipher cipher = Cipher.getInstance(cipherName);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(in, s, len);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decode(byte[] in) {
        return decode(in, 0, in.length);
    }

    public static byte[] encode(byte[] in, int s, int len) {
        Key secretKey = aesKey;
        try {
            Cipher cipher = Cipher.getInstance(cipherName);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(in, s, len);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encode(byte[] in) {
        return encode(in, 0, in.length);
    }
}
