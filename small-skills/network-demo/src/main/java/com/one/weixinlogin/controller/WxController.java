package com.one.weixinlogin.controller;

import com.google.gson.Gson;
import com.one.weixinlogin.util.ConstantPropertiesUtil;
import com.one.weixinlogin.util.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * 注意这里的请求路径不能瞎写,必须与redict_url中路径保持一致
 *
 * @ClassName: WxController
 * @Description: 微信登录的后台请求接口
 * @Author: one
 * @Date: 2021/03/02
 */
@Controller
@RequestMapping("/wx")
public class WxController {
    @GetMapping("/login")
    public String login(){
        //1.获取请求code
        //---------------------------------------------
        //1.1拼接请求地址  %s是占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //1.2 设置状态，前置域名
        //为了让大家能够使用微信回调跳转服务器，这里填写你在ngrok的前置域名
        String state="wanjunjie";
        //1.3回调地址要进行url编码
        String redirectUrl= ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //1.4对请求地址进行赋值操作
        /**
         *  String.format(String,object...) 对之前的占位符进行赋值
         *  后面的参数是对之前的字符串的占位符进行赋值
         */
        baseUrl = String.format(baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );
        //1.5重定向到展示二维码地址
        return "redirect:"+baseUrl;
    }

    /**
     * 回调方法
     * @param code 001Hs7Ga19Gd3B0hxoIa1r4fDH1Hs7Gk 扫码之后微信官方生成的code
     * @param state "wanjunjie" 自己设置的状态参数,微信请求重定向时会携带该参数
     * @return
     */
    @GetMapping("/callback")
    @ResponseBody
    public Map callback(String code,String state) {
        System.out.println("code:" + code);
        System.out.println("state:" + state);
        // 根据code获取access_token的值
        // 获取access_token的请求地址
        //2. 通过code获取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //3.对请求地址进行赋值操作
        baseAccessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );
        try {
            String result = HttpClientUtils.get(baseAccessTokenUrl);
            //4.根据access_token和openid获取用户个人信息
            //4.1 声明获取用户信息的api接口
            String userInfoUrl="https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //4.2取值操作
            Gson gson=new Gson();
            Map map = gson.fromJson(result, Map.class);
            String access_token= (String) map.get("access_token");
            String openid= (String) map.get("openid");
            //4.3赋值操作
            userInfoUrl=String.format(userInfoUrl,
                    access_token,
                    openid);
            String userInfoResult = HttpClientUtils.get(userInfoUrl);

            //4.5 把当前userInfoResult中的信息展示到页面上
            Map gsonMap = gson.fromJson(userInfoResult, Map.class);
            System.out.println(gsonMap);
            return gsonMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
