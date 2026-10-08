package com.one.classloader;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GroovyExecutor {
    
    private final GroovyClassLoader groovyLoader;
    
    public GroovyExecutor() {
        // 用当前类的类加载器，比 TCCL 更稳
        ClassLoader appLoader = this.getClass().getClassLoader();
//        ClassLoader threadClasLoader = Thread.currentThread().getContextClassLoader();
        
        CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding("UTF-8");
        
        // 如果是 URLClassLoader（Spring Boot 的 LaunchedURLClassLoader 是子类），
        // 把它的所有 URL 注入给 Groovy 编译器
        if (appLoader instanceof URLClassLoader urlClassLoader) {
            URL[] urls = urlClassLoader.getURLs();
            String cp = Arrays.stream(urls)
                .map(URL::toString)
                .collect(Collectors.joining(File.pathSeparator));
            config.setClasspath(cp);
        }
        
        this.groovyLoader = new GroovyClassLoader(appLoader, config);
    }
}