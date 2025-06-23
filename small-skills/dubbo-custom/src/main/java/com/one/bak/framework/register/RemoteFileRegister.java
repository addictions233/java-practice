package com.one.bak.framework.register;

import com.one.bak.framework.URL;

import java.io.*;
import java.util.*;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class RemoteFileRegister {

    private static final Map<String, List<URL>> REGISTER = new HashMap<>();

    /**
     * provider注册服务
     */
    public static void register(String interfaceName, URL url) {
        REGISTER.computeIfAbsent(interfaceName, key -> {
            List<URL> list = new LinkedList<>();
            list.add(url);
            return list;
        }).add(url);

        // 保存到本地文件中, 使用本地文件模拟远程注册中心
        saveFile();
    }

    private static void saveFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("/fileRegister.text");) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(REGISTER);
            objectOutputStream.flush();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * consumer通过interfaceName获取所有的provider的url
     * @param interfaceName 接口名称
     */
    public static List<URL> get(String interfaceName) {
        Map<String, List<URL>> register = loadFile();
        return register.get(interfaceName);
    }

    private static Map<String, List<URL>> loadFile() {
        try (FileInputStream fileInputStream = new FileInputStream("/fileRegister.text")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
