package com.one.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwtdemo2Tests {

    private static final String SECRETKEY = "123456";

    @Test
    public void test() {
        //创建token
        Map<String, Object> claums = new HashMap<>();
        claums.put("username", "mike");
        claums.put("mobile", "159xxxxxxxx");
        claums.put("expires_in", 60*1000); //设置过期时间，可用于过期校验
        MacSigner rsaSigner = new MacSigner(SECRETKEY);
        Jwt encode = JwtHelper.encode(JSON.toJSONString(claums), rsaSigner);
        String token = encode.getEncoded();
        System.out.println(token);

        //解析token
        Jwt decode = JwtHelper.decode(token);
        System.out.println(decode.getClaims());

    }


}
