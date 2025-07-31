package com.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author one
 * 它作为 Web 容器、Camunda 引擎和 Camunda Web 应用程序资源嵌入到 Tomcat 中，并使用了H2 数据库。
 */
@SpringBootApplication
public class CamundaApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CamundaApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (path == null || "".equals(path)) {
            path = "/";
        }
        System.out.println("\n----------------------------------------------------------\n" +
                "\tCamunda7Application is running!\n" +
                "\tPort:\t" + port + "\n" +
                "\tPath:\t" + path + "\n" +
                "----------------------------------------------------------");
    }
}