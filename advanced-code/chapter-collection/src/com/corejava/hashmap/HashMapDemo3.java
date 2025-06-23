package com.corejava.hashmap;

import com.corejava.bean.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapDemo3 {
    public static void main(String[] args) {
        HashMap<String, Student> hm = new HashMap<>();
        hm.put("heima001",new Student("zhangsan",23));
        hm.put("heima002",new Student("lisi",24));
        hm.put("heima003",new Student("wangwu",25));
        Set<Map.Entry<String, Student>> entries = hm.entrySet();
        for (Map.Entry<String, Student> entry : entries) {
            String key = entry.getKey();
            Student value = entry.getValue();
            System.out.println("Key:"+key+",name:"+value.getName()+",age:"+value.getAge());
        }
        System.out.println("-----------");

        Set<String> keys = hm.keySet();
        for (String key : keys) {
            System.out.println("key:"+key+",name:"+hm.get(key).getName()+",age:"+hm.get(key).getAge());
        }
    }
}
