package one.service;

import com.one.UserRequest;
import com.one.api.UserService;
import com.one.entity.User;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author one
 */
@DubboService(protocol = "tri")
public class UserServiceImpl implements UserService {

    /**
     * 测试使用protobuf
     */
    public com.one.User getUser(UserRequest userRequest) {
        com.one.User user = com.one.User.newBuilder().setUid(userRequest.getUid()).setUsername("zhouyu").build();
        return user;
    }

    @Override
    public User getUser(String uid) {
        return new User(uid, "zhangsan");
    }


    /**
     * 服务端分多批次返回结果
     * 服务端流方法只能返回void, 用response流返回结果
     */
    @Override
    public void sayHelloServerStream(String request, StreamObserver<String> response) {
        System.out.println("服务端接收到的请求:" + request);
        response.onNext("结果1");
        // TODO ....
        response.onNext("结果2");
        // TODO ...
        response.onNext("结果3");
        response.onCompleted();
    }

    /**
     * 双端流两边都可以发送数据：客户端和服务器都可以在一次连接中发送多次数据
     */
    @Override
    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接收到客户端发来的数据:" + data);
                response.onNext("hello," + data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("completed!");
            }
        };
    }
}
