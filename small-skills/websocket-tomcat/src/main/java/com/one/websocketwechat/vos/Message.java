package com.one.websocketwechat.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @ClassName: Message
 * @Description: 客户端发送到服务器的消息, 格式如下:
 *                      - 发送给指定好友的消息:  {"toName" : "张三", "message":"你好"}
 * @Author: one
 * @Date: 2021/05/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Message implements Serializable {
    private static final long serialVersionUID = -3264122138895719058L;
    /**
     *  发送消息给哪个好友
     */
    private String toName;

    /**
     * 发送的消息内容
     */
    private String message;
}
