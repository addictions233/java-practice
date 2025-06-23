package com.one.sharding.encrypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
 
/**
 * 使用AES对称加密算法的CBC模式进行加解密
 */
public class AESCBCUtil {
    //使用AES算法的 CBC模式 和 PKCS7填充
    private static final String DEFAULT_ALG_STRING = "AES/CBC/PKCS7Padding";
 
    static {
        //向Java的Security系统添加一个新的安全提供者（Provider），即 BouncyCastle提供者
        //BouncyCastle是一个广泛使用的开源加密库，它提供了大量的加密算法实现，包括一些不在Java标准版中的算法(比如，国密算法SM4等等)
        Security.addProvider(new BouncyCastleProvider());
    }
 
 
    /**
     * 初始化Cipher
     *
     * @param encrypt 判断是加密还是解密操作，true表示进行加密
     * @param key     密钥
     * @param iv      初始化向量（CBC模式是必须的）
     * @return
     */
    private static Cipher init(boolean encrypt, String key, String iv) {
        AlgorithmParameters params;
        try {
            params = AlgorithmParameters.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        try {
            //设置iv参数
            params.init(new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidParameterSpecException e) {
            throw new IllegalStateException(e);
        }
 
        //我们已经从外部导入了密钥，因此不需要使用KeyGenerator生成随机的密钥
        //使用 SecretKeySpec，直接根据字节数组生成一个密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher;
        try {
            //创建Cipher实例，并指定加密算法（AES）、模式（CSC）和填充方式（PKCS7）
            cipher = Cipher.getInstance(DEFAULT_ALG_STRING);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("无法获取AES算法实例", e);
        } catch (NoSuchPaddingException e) {
            throw new IllegalStateException("无法获取算法Padding:" + DEFAULT_ALG_STRING, e);
        }
        int mode;
        if (encrypt) {
            //指定操作模式为【加密】
            mode = Cipher.ENCRYPT_MODE;
        } else {
            //指定操作模式为【解密】
            mode = Cipher.DECRYPT_MODE;
        }
        try {
            //根据mode的不同，就能判断是加密操作，还是解密操作
            cipher.init(mode, secretKeySpec, params);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalStateException(e);
        }
        return cipher;
    }
 
    /**
     * @param data 明文数据
     * @param key  密钥
     * @param iv
     * @return
     */
    public static String encrypt(String data, String key, String iv) {
        if (data == null) {
            return null;
        }
        //初始化一个加密的Cipher
        Cipher cipher = init(true, key, iv);
 
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] encryptBytes = null;
        try {
            //执行实际的加密操作
            encryptBytes = cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException(e);
        }
        //使用Base64对字节数组进行加密，获得一个加密数组
        byte[] encode = Base64.getEncoder().encode(encryptBytes);
 
        return new String(encode, StandardCharsets.UTF_8);
    }
 
    /**
     * @param encryptData 加密数据
     * @param key         使用加密用到的密钥
     * @param iv          使用加密用到的iv
     * @return
     */
    public static String decrypt(String encryptData, String key, String iv) {
        //初始化一个解密的Cipher
        Cipher cipher = init(false, key, iv);
        if (encryptData == null) {
            return null;
        }
        //通过Base64，先将加密串解密（因为加密数据是通过Base64加密生成的）
        byte[] bytes = Base64.getDecoder().decode(encryptData);
        byte[] decryptBytes = null;
        try {
            //执行实际的解密操作
            decryptBytes = cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException(e);
        }
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }
 
}