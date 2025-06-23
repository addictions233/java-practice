package com.corejava.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *  键盘录入一个字符串,统计每个字符出现的次数
 */
public class HashMapTest1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一串字符串:");
        String str = sc.nextLine();
        HashMap<Character,Integer> hm = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(hm.containsKey(ch)){
                hm.put(ch,hm.get(ch)+1);
            } else{
                hm.put(ch,1);
            }
        }
        Set<Map.Entry<Character, Integer>> entries = hm.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            System.out.println(entry);
        }

    }
}
