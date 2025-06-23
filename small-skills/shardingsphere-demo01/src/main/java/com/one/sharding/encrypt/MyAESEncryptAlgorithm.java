//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.one.sharding.encrypt;

import com.google.common.base.Preconditions;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shardingsphere.encrypt.spi.EncryptAlgorithm;
import org.apache.shardingsphere.encrypt.spi.context.EncryptContext;

public final class MyAESEncryptAlgorithm implements EncryptAlgorithm<Object, String> {
    private static final String AES_KEY = "aes-key-value";
    private Properties props;
    private byte[] secretKey;

    public MyAESEncryptAlgorithm() {
    }

    @Override
    public void init(Properties props) {
        this.props = props;
        this.secretKey = this.createSecretKey(props);
    }

    private byte[] createSecretKey(Properties props) {
        Preconditions.checkArgument(props.containsKey("aes-key-value"), "%s can not be null.", "aes-key-value");
        return Arrays.copyOf(DigestUtils.sha1(props.getProperty("aes-key-value")), 16);
    }

    @Override
    public String encrypt(Object plainValue, EncryptContext encryptContext) {
        try {
            if (null == plainValue) {
                return null;
            } else {
                byte[] result = this.getCipher(1).doFinal(String.valueOf(plainValue).getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(result);
            }
        } catch (GeneralSecurityException var4) {
            throw new RuntimeException(var4);
        }
    }

    @Override
    public Object decrypt(String cipherValue, EncryptContext encryptContext) {
        try {
            if (null == cipherValue) {
                return null;
            } else {
                byte[] result = this.getCipher(2).doFinal(Base64.getDecoder().decode(cipherValue));
                return new String(result, StandardCharsets.UTF_8);
            }
        } catch (GeneralSecurityException var4) {
            throw new RuntimeException(var4);
        }
    }


    private Cipher getCipher(int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher result = Cipher.getInstance(this.getType());
        result.init(decryptMode, new SecretKeySpec(this.secretKey, this.getType()));
        return result;
    }

    @Override
    public String getType() {
        return "AES";
    }

    @Override
    public Properties getProps() {
        return this.props;
    }
}
