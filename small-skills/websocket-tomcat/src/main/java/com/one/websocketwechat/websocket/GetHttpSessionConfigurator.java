package com.one.websocketwechat.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

/**
 * @ClassName: GetHttpSessionConfiguration
 * @Description: 在websocket的请求中获取httpsession对象
 * @Author: one
 * @Date: 2021/05/17
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        // 由于ServerEndpointConfig类继承了EndpointConfig类
        // 所以src对象本质上是一个 EndpointConfig对象, 我们可以将httpSession对象存储在config对象中
        // 然后在onOpen()方法中通过EndpointConfig对象获取httpSession对象
        Map<String, Object> userProperties = config.getUserProperties();
        // 将httpSession对象中存储在map集合中, 这里的key只要能唯一的表示HttpSession对象就行,我们这里用它的字节码对象名称
        // 一个类具有唯一的字节码对象,每个客户端都会创建一个ServerEndpointConfig对象,不存在线程安全问题
        userProperties.put(HttpSession.class.getName(),httpSession);
    }
}
