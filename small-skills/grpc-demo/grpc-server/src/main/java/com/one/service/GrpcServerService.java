/*
 * Copyright (c) 2016-2023 The gRPC-Spring Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.one.service;

import com.et.grpc.api.protobuf.lib.HelloReply;
import com.et.grpc.api.protobuf.lib.HelloRequest;
import com.et.grpc.api.protobuf.lib.MyServiceGrpc;
import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author one
 * grpc服务端的使用demo
 */

@GrpcService
public class GrpcServerService extends MyServiceGrpc.MyServiceImplBase {

    /**
     * 注解@GrpcService作用
     *   在基于 Spring Boot 的 gRPC 服务中，这个注解它的作用是将 gRPC 服务实现类标记为可被 gRPC 服务器扫描和注册的组件。
     * 注解@GrpcService的作用包括：
     * 1.服务注册：通过将 @GrpcService 注解应用于 gRPC 服务实现类，将其自动注册到 gRPC 服务器中。
     *   这样，gRPC 服务器能够知道有哪些服务可用，并能够处理客户端的请求。
     * 2.依赖注入：在基于 Spring Boot 的应用中，@GrpcService 注解通常与依赖注入框架（如 Spring）一起使用。
     *   当使用 @GrpcService 注解标记服务实现类时，依赖注入框架会自动扫描并创建该类的实例，并将其注入到 gRPC 服务器中。
     *   这使得你可以在服务实现类中使用其他依赖注入的组件或进行相关的业务逻辑。
     * 3.简化配置：使用 @GrpcService 注解可以简化 gRPC 服务的配置。通过标记服务实现类，你无需手动编写繁琐的注册和配置代码，
     *   而是让注解和依赖注入框架处理这些细节，使得服务的开发更加方便和高效。
     *   需要注意的是，@GrpcService 注解是针对基于 Spring Boot 的项目，并与 gRPC 框架集成时的一种常用做法。
     *   如果你使用其他的 gRPC 框架或平台，可能会有不同的方式来注册和管理 gRPC 服务实现类。
     */
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
