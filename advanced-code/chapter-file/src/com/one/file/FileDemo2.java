package com.one.file;

import java.io.File;
import java.util.HashMap;

/**
 * @author one
 *  统计一个文件夹里面,各种文件后缀名的文件数量是多少?
 */
public class FileDemo2 {
    static HashMap<String,Integer> hm = new HashMap<>();
    public static void main(String[] args) {
        File file = new File("My_Day10");
        countFile(file);
        System.out.println(hm);
    }
    public static void countFile(File file){
        if(file == null) {
            return;
        }
        if(file.isFile()) {
            String[] split = file.getName().split("\\.");
            String suffix = split[split.length - 1];
            if (hm.containsKey(suffix)) {
                hm.put(suffix,hm.get(suffix) + 1);
            } else {
                hm.put(suffix,1);
            }
        } else{
            File[] files = file.listFiles();
            for (File f : files) {
                countFile(f);
            }
        }
    }
}
