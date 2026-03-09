package com.one.bootstrapregistryinitializer;

import com.one.controller.UserController;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;

/**
 * 构建BootstrapContext的初始化器
 */
public class MyBootstrapRegistryInitializer implements BootstrapRegistryInitializer {
    @Override
    public void initialize(BootstrapRegistry registry) {
        registry.register(UserController.class, new BootstrapRegistry.InstanceSupplier<UserController>() {
            @Override
            public UserController get(BootstrapContext context) {
                return new UserController();
            }
        });
    }
}
