package com.one.netty.server.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Netty使用中将所有的message都封装成Message抽象类的实现类对象
 * @author one
 */
@Data
public abstract class Message implements Serializable {

    /**
     * 根据消息类型字节，获得对应的消息 class
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    /**
     * 按照消息类型
     */
    public static final int LOGIN_REQUEST_MESSAGE = 0;
    public static final int LOGIN_RESPONSE_MESSAGE = 1;
    public static final int CHAT_REQUEST_MESSAGE = 2;
    public static final int CHAT_RESPONSE_MESSAGE = 3;
    public static final int PING_MESSAGE = 14;
    public static final int PONG_MESSAGE = 15;
    /**
     * 请求类型 byte 值
     */
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    /**
     * 响应类型 byte 值
     */
    public static final int  RPC_MESSAGE_TYPE_RESPONSE = 102;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LOGIN_REQUEST_MESSAGE, LoginRequestMessage.class);
        messageClasses.put(LOGIN_RESPONSE_MESSAGE, LoginResponseMessage.class);
        messageClasses.put(CHAT_REQUEST_MESSAGE, ChatRequestMessage.class);
        messageClasses.put(CHAT_RESPONSE_MESSAGE, ChatResponseMessage.class);
        messageClasses.put(RPC_MESSAGE_TYPE_REQUEST, RpcRequestMessage.class);
        messageClasses.put(RPC_MESSAGE_TYPE_RESPONSE, RpcResponseMessage.class);
    }

}
