package com.one.websocketwechat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.websocketwechat.vos.ResultMessage;

/**
 * @ClassName: MessageUtil
 * @Description: 用来封装消息的工具类
 * @Author: one
 * @Date: 2021/05/15
 */
public class MessageUtil {

    /**
     * jackson包下的用来生成和解析json的工具对象, 类似于FastJson中的JSON对象
     * 一般设置为静态成员变量,可能在多个方法中使用到mapper对象
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将 message消息对象序列化为json格式的字符串
     * @param isSystem 是否是系统消息
     * @param fromName 从谁那里发出的消息
     * @param message 消息内容
     * @return json
     */
    public static String getMessage(boolean isSystem, String fromName, Object message) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setSystem(isSystem);
        resultMessage.setMessage(message);
        if ( null != fromName) {
            resultMessage.setFromName(fromName);
        }
        try {
            return mapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
