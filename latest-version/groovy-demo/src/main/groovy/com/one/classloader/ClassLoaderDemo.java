package com.one.classloader;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println(ReentrantLock.class.getClassLoader());
        CompilerConfiguration configuration = new CompilerConfiguration();
        List<CompilationCustomizer> customizers = configuration.getCompilationCustomizers();
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        System.out.println(groovyClassLoader.getParent());
        System.out.println(ClassLoaderDemo.class.getClassLoader().getParent());
    }
}
