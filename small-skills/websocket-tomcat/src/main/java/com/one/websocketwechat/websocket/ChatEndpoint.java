package com.one.websocketwechat.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.websocketwechat.utils.MessageUtil;
import com.one.websocketwechat.vos.Message;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ChatEndPoint
 * @Description: 使用tomcat提供的websocket用两种方式:
 *                      第一种: 继承javax.websocket.Endpoint抽象类, 并重新其方法
 *                      第二种: 基于注解的方式 (更加简单) @ServerEndpoint @OnOpen等注解
 * 注解@ServerEndpoint: 用于自动websocket请求的服务端的请求路径, 等同于http请求中的 @WebServlet("/chat")注解
 * 每次客户端和服务端建立websocket连接,服务端都会创建一个ChatEndpoint对象,所以并不是单例的
 * @Author: one
 * @Date: 2021/05/16
 */
@Component
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
//public class ChatEndpoint extends Endpoint
public class ChatEndpoint {
    /**
     * 静态成员变量是所有对象共享的,一个类所有的对象公用一个静态成员变量,而普通成员变量是每个对象独有的
     * 每一个客户端连接都会创建一个ChatEndpoint对象, 本map集合用来存储每一个客户端对象对应的ChatEndpoint对象
     * key=每个客户端的用户名  value=每个客户端的ChatEndpoint对象
     */
    private static final Map<String,ChatEndpoint> ONLINE_USERS = new ConcurrentHashMap<>();

    /**
     * 声明session对象, 通过Session对象可以发送消息给指定的用户(客户端),
     * 该对象不能定义为静态,因为每个客户端都会创建一个新的Session对象
     */
    private Session session;

    /**
     * 就是在javaEE中定义的HttpSession对象,与Websocket无关,但是我们需要在HttpSession对象中获取之前存储的数据内容
     * 用来获取之前在HttpSession中存储的用户名等内容, 如何在本类中获取HttpSession对象?
     * 我们的解决方法是: 在自定义配置类GetHttpSessionConfigurator对象中,我们将HttpSession对象存储到
     * EndpointConfig对象的UserProperties属性这个map集合中,然后在onOpen()方法中获取HttpSession对象
     */
    private HttpSession httpSession;

    /**
     * 创建连接时被调用本方法
     * @param session websocket建立连接时就会自动创建Session对象
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;

        //从httpSession对象中获取我们存储的用户名
        String username = (String)httpSession.getAttribute("user");
        // 将当前对象(ChatEndpoint对象)存储在容器中, key=username , value=ChatEndpoint对象
        ONLINE_USERS.put(username,this);

        // 将当前在线的所有的用户的用户名都广播推送给所有的  用户
        String message = MessageUtil.getMessage(true, null, getNames());
        // 向所有的用户广播消息
        broadcastAllUsers(message);
    }

    /**
     * 获取所有的在线用户的用户名
     */
    private Set<String> getNames() {
        return ONLINE_USERS.keySet();
    }

    /**
     * 将在线的用户的用户名称广播给所有的用户
     */
    private void broadcastAllUsers(String message){
        try {
            // 将消息发送到所有的客户端, 消息内容为所有的在线用户名称
            Set<String> names = ONLINE_USERS.keySet();
            for (String name : names) {
                // 通过用户的用户名,获取每一个用户对应的ChatEndpoint对象
                ChatEndpoint chatEndpoint = ONLINE_USERS.get(name);
                // 通过ChatEndpoint类的session属性将消息发送到指定的客户端,循环一次发送给一个客户端
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接收到客户端发送的数据时被调用
     */
    @OnMessage
    public void onMessage(String message, Session session){
        try {
            // 首先使用Jackson解析客户端发送过来的message对象
            ObjectMapper mapper =  new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            // 获取message中消息内容和发送对象
            String sendMessage = mess.getMessage();
            // 消息要发给谁
            String sendTarget = mess.getToName();
            // 获取想要发送消息的用户(即消息是谁发的)
            String username = (String)this.httpSession.getAttribute("user");
            // 服务端创建要发送的消息的消息对象
            String resultMessage = MessageUtil.getMessage(false, username, sendMessage);
            // 发送消息, 获取目标对象的chatEndpoint对象
            ChatEndpoint chatEndpoint = ONLINE_USERS.get(sendTarget);
            // 用目标对象的chatEndpoint对象发送消息给目标对象
            chatEndpoint.session.getBasicRemote().sendText(resultMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 连接关闭时被调用
     */
    @OnClose
    public void onClose(Session session){
        // 关闭连接时,要将该会话的ChatEndpoint对象从集合中删除,并发送广播消息更新在线人数
        String username = (String)this.httpSession.getAttribute("user");
        ONLINE_USERS.remove(username);
        String message = MessageUtil.getMessage(true, null, getNames());
        // 将更新后的在线用户名称广播给所有的客户端
        broadcastAllUsers(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
