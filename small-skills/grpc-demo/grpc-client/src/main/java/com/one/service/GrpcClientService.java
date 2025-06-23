package com.one.service;

import com.et.grpc.api.protobuf.lib.HelloReply;
import com.et.grpc.api.protobuf.lib.HelloRequest;
import com.et.grpc.api.protobuf.lib.MyServiceGrpc;
import org.springframework.stereotype.Service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;


/**
 * @author one
 * 客户端使用demo
 */
@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    // @GrpcClient("cloud-grpc-server")
    private MyServiceGrpc.MyServiceBlockingStub myServiceStub;

    public String sendMessage(final String name) {
        try {
            final HelloReply response = this.myServiceStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

}
