package com.one.service;

/**
 * @author one
 */
public interface OrderService {
    String sayHello(String name);

    void sayHelloServerStream(String name);

    void sayHelloStream(String name);

    String createOrder();
}
