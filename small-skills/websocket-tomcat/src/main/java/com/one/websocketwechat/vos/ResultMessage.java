package com.one.websocketwechat.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @ClassName: ResultMessage
 * @Description: 服务器发送到客户端的消息, 分为下面两种:
 *                  - 系统消息格式: { "isSystem" : true, "fromName" : null, "message": ["李四", "王五"]}
 *                  - 推送给某一个人的消息格式: {"isSystem" : true, "fromName" : "张三" , "message" :  "你好"}
 * @Author: one
 * @Date: 2021/05/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ResultMessage implements Serializable {
    private static final long serialVersionUID = 7068810238282352356L;
    /**
     * 是否是系统发送的消息
     */
    private boolean isSystem;

    /**
     * 从哪个好友发送来的消息
     */
    private String fromName;

    /**
     * 消息的内容
     */
    private Object message;
}
