package com.corejava.hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapDemo2 {
    public static void main(String[] args) {
        HashMap<String,String> hm = new HashMap<>();
        hm.put("heima001","zhangsan");
        hm.put("heima002","lisi");
        hm.put("heima003","wangwu");
        Set<Map.Entry<String, String>> entries = hm.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key:"+key+"---value:"+value);
        }
        System.out.println("-------------");

        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
    }
}
