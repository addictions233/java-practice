package com.one.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatMessageHandler extends TextWebSocketHandler {
    /**
     * 存储用户
     */
    private static final ArrayList<WebSocketSession> USER_LIST = new ArrayList<>();

    private SimpMessagingTemplate messagingTemplate;

    public ChatMessageHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 连接成功时候
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        USER_LIST.add(session);
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        // TextMessage returnMessage = new TextMessage("你将收到的离线");
        // session.sendMessage(returnMessage);
    }

    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sendMessageToUsers(message);
        //super.handleTextMessage(session, message);
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName 用户名
     * @param message 消息
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : USER_LIST) {
            if (user.getAttributes().get("websocket_username").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : USER_LIST) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("websocket connection closed......");
        USER_LIST.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("websocket connection closed......");
        USER_LIST.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
} 