package com.one.weixinlogin.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *常量类，获取资质信息
 *InitializingBean接口为bean提供了初始化方法的方式，
 *   它只包括afterPropertiesSet()方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 *
 * @author one
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    /**
     * 应用id
     */
    @Value("${wx.open.app_id}")
    private String appId;

    /**
     * 应用秘钥
     */
    @Value("${wx.open.app_secret}")
    private String appSecret;

    /**
     * 回调地址
     */
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;


    /**
     * 实现 InitializingBean接口之后必须重写的方法
     * 在创建Bean对象时必须执行afterPropertiesSet()方法
     * 本质上就是一个常量类,读取配置文件对属性进行赋值
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID=appId;
        WX_OPEN_APP_SECRET=appSecret;
        WX_OPEN_REDIRECT_URL=redirectUrl;
    }
}