package com.one.websocketwechat.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @ClassName: Result
 * @Description: 用于登陆响应回给浏览器的结果对象
 * @Author: one
 * @Date: 2021/05/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class LoginResult implements Serializable {
    private static final long serialVersionUID = -4366484833867062838L;
    /**
     * 是否登陆
     */
    private boolean flag;
    /**
     * 消息内容
     */
    private String message;
}
