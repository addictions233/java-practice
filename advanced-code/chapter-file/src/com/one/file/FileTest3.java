package com.one.file;

import java.io.File;
import java.util.HashMap;

/**
 * @ClassName: FileTest3
 * @Description: 计算一个目录下所用的文件名的格式
 * @Author: one
 * @Date: 2021/07/21
 */
public class FileTest3 {
    public static void main(String[] args) {
        HashMap<String,Integer> map = new HashMap<>();
        File file = new File("E:\\IdeaProjects\\Advanced_Code_119\\My_Day11");
        getFileNumber(file,map);
        System.out.println(map);
    }

    /**
     * 统计一个目录下所用的文件的个数
     * @param file file
     */
    public static void getFileNumber(File file, HashMap<String,Integer> map){
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                // 正则表达式需要转义.
                String[] split = file1.getName().split("\\.");
                // 对文件名进行切割,取最后一个数组值作为文件名后缀
                String suffix = split[split.length-1];
                if (map.containsKey(suffix)) {
                    map.put(suffix,map.get(suffix) + 1);
                } else {
                    map.put(suffix,1);
                }
            } else {
                getFileNumber(file1,map);
            }
        }
    }
}
