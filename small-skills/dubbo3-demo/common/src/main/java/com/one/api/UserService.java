package com.one.api;

import com.one.entity.User;
import org.apache.dubbo.common.stream.StreamObserver;

/**
 * dubbo3支持的协议: rest, dubbo, triple  以接口的方式支持调用
 * @author one
 */
public interface UserService {

    User getUser(String uid);

    /**
     * SERVER_STREAM 服务端流
     */
    void sayHelloServerStream(String request, StreamObserver<String> response);

    /**
     * BI_STREAM 双端流
     */
    StreamObserver<String> sayHelloStream(StreamObserver<String> response);


}
