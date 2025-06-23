package com.corejava.sort;
/**
 *  模拟斗地主发牌并进行排序
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ArrayListTest {
    public static void main(String[] args) {
        //定义一个集合用来存储牌
        ArrayList<String> list = new ArrayList<>();
        //用字符串的拼接创建各个牌
        String[] flowers = {"♠","♥","♣","♦"};
        String[] numbers = {"3","4","5","6","7","8","9","10","J","Q","K","A","2"};
        for (int i = 0; i < flowers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                list.add(flowers[i]+numbers[j]);
            }
        }
//        System.out.println(list);
        list.add("joker");
        list.add("JOKER");
        list.add("癞子");

        //给每个牌建立索引,然后利用索引对每副牌进行排序
        ArrayList<Character> list2 = new ArrayList<>();
        Collections.addAll(list2,'3','4','5','6','7','8','9','1','J','Q','K','A','2','o','O','子');
        Comparator<String> comparator = (o1,o2)-> list2.indexOf(o2.charAt(1))-list2.indexOf((o1.charAt(1)));

        //将牌组随机打乱
        Collections.shuffle(list);

        //发牌
        ArrayList<String> 周润发 = new ArrayList<>();
        ArrayList<String> 刘德华 = new ArrayList<>();
        ArrayList<String> 周星驰 = new ArrayList<>();
        ArrayList<String> 地主牌 = new ArrayList<>();

        地主牌.add(list.get(list.size()-1));
        地主牌.add(list.get(list.size()-2));
        地主牌.add(list.get(list.size()-3));
        地主牌.add(list.get(list.size()-4));

        for (int i = 0; i < list.size()-4; i++) {
            switch(i%3){
                case 1:
                    周润发.add(list.get(i));
                    break;
                case 2:
                    刘德华.add(list.get(i));
                    break;
                default:
                    周星驰.add(list.get(i));
                    break;
            }
        }

        Collections.sort(周润发,comparator);
        Collections.sort(刘德华,comparator);
        Collections.sort(周星驰,comparator);
        Collections.sort(地主牌,comparator);

        System.out.println("地主牌 = " + 地主牌);
        System.out.println("周润发 = " + 周润发);
        System.out.println("刘德华 = " + 刘德华);
        System.out.println("周星驰 = " + 周星驰);

    }
}
