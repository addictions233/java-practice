package com.one.sharding.encrypt;

/**
 * @author mqp
 * @date 2021/10/30 9:49
 */

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 加密解密
 *
 * @author mqp
 */
public class RSAEncryptUtil {

    /**
     * 非对称密钥算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 密钥长度
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 分块加密长度
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 公钥类型
     */
    private static final int PUBLIC_SECRET = 1;

    /**
     * 私钥类型
     */
    private static final int PRIVATE_SECRET = 2;

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static RSAEncryptProperties genKeyPair() throws Exception {
        RSAEncryptProperties rsaEncryptProperties = new RSAEncryptProperties();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        rsaEncryptProperties.setRsaPrivateKey(privateKey);
        rsaEncryptProperties.setRsaPublicKey(publicKey);
        return rsaEncryptProperties;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encodeToString(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decodeFromString(sign));
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
        return processBlock(encryptedData, privateKey, PRIVATE_SECRET, Cipher.DECRYPT_MODE);
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey) throws Exception {
        return processBlock(encryptedData, publicKey, PUBLIC_SECRET, Cipher.DECRYPT_MODE);
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        return processBlock(data, publicKey, PUBLIC_SECRET, Cipher.ENCRYPT_MODE);
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        return processBlock(data, privateKey, PRIVATE_SECRET, Cipher.ENCRYPT_MODE);
    }

    /**
     * 分段加密
     *
     * @param dataStr
     * @param key
     * @param keyType 1:公钥，2：私钥
     * @param mode
     * @return
     * @throws Exception
     */
    private static String processBlock(String dataStr, String key, int keyType, int mode) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Key keySecret = null;
        if (keyType == PUBLIC_SECRET) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            keySecret = keyFactory.generatePublic(x509KeySpec);
        }
        if (keyType == PRIVATE_SECRET) {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            keySecret = keyFactory.generatePrivate(pkcs8KeySpec);
        }
        int maxSize = (mode == Cipher.ENCRYPT_MODE ? MAX_ENCRYPT_BLOCK : MAX_DECRYPT_BLOCK);
        cipher.init(mode, keySecret);
        byte[] data = (mode == Cipher.ENCRYPT_MODE ? dataStr.getBytes() : Base64Utils.decodeFromString(dataStr));
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxSize) {
                cache = cipher.doFinal(data, offSet, maxSize);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxSize;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        if (mode == Cipher.ENCRYPT_MODE) {
            return Base64Utils.encodeToString(encryptedData);
        }
        if (mode == Cipher.DECRYPT_MODE) {
            return new String(encryptedData);
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param rsaEncryptProperties 密钥对
     * @return
     */
    public static String getPrivateKey(RSAEncryptProperties rsaEncryptProperties) {
        Key key = rsaEncryptProperties.getRsaPrivateKey();
        return Base64Utils.encodeToString(key.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param rsaEncryptProperties 密钥对
     * @return
     */
    public static String getPublicKey(RSAEncryptProperties rsaEncryptProperties) {
        Key key = rsaEncryptProperties.getRsaPublicKey();
        return Base64Utils.encodeToString(key.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String content = "Hello, world!";
        RSAEncryptProperties rsaEncryptProperties = genKeyPair();
        String publicKey = getPublicKey(rsaEncryptProperties);
        String privateKey = getPrivateKey(rsaEncryptProperties);
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        System.out.println("明文：" + content);
        String pubKeyEncrypt = encryptByPublicKey(content, publicKey);
        String signContent = sign(content.getBytes(), privateKey);
        System.out.println("1.私钥加签：" + signContent);
        System.out.println("2.验签结果：" + verify(content.getBytes(), publicKey, signContent));
        System.out.println("3.公钥加密：" + pubKeyEncrypt);
        System.out.println("4.私钥解密：" + decryptByPrivateKey(pubKeyEncrypt, privateKey));

    }

    public static class RSAEncryptProperties {

        /**
         * 公钥
         */
        private RSAPublicKey rsaPublicKey;

        /**
         * 私钥
         */
        private RSAPrivateKey rsaPrivateKey;

        public RSAPublicKey getRsaPublicKey() {
            return rsaPublicKey;
        }

        public void setRsaPublicKey(RSAPublicKey rsaPublicKey) {
            this.rsaPublicKey = rsaPublicKey;
        }

        public RSAPrivateKey getRsaPrivateKey() {
            return rsaPrivateKey;
        }

        public void setRsaPrivateKey(RSAPrivateKey rsaPrivateKey) {
            this.rsaPrivateKey = rsaPrivateKey;
        }
    }

}
