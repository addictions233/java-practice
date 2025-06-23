package com.corejava.hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HashMapDemo1 {
    public static void main(String[] args) {
        HashMap<String,String> hm = new HashMap<>();
        hm.put("heima001","zhangsan");
        hm.put("heima002","lisi");
        hm.put("heima003","wangwu");
        System.out.println("hm:"+hm);
        Set<String> keys = hm.keySet();
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = hm.get(key);
            System.out.println("key:"+key+"----value:"+value);
        }
        for (String key : keys) {
            String value = hm.get(key);
            System.out.println("key:"+key+"----value:"+value);
        }
    }
}
