package com.one.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @author one
 * Rest协议支持: 目前rest协议仅支持Java接口服务定义模式，相比于dubbo和triple协议，
 * rest场景下我们需要为Interface增加注解，支持Spring MVC、JAX_RS两种注解
 */
@Path("/hello")
public interface HelloService {

    @GET
    @Path("/say/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    String sayHello(@PathParam("name") String name);
}
