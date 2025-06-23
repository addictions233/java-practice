package com.corejava.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 案例需求:
 *      统计一个文本文件里面所有的长单词的个数
 *      1, 用传统的集合处理方式   2,用stream流来处理 两者对比
 *      用到的方法
 *      Files工具类:static byte[]	readAllBytes​(Path path)
 *              Reads all the bytes from a file.
 *      Paths类 :  static Path	get​(String first, String... more)
 *          Converts a path string, or a sequence of strings that when joined form a path string, to a Path
 */
public class StreamDemo1_1 {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("Charpter_Stream\\alice.txt")), StandardCharsets.UTF_8);
        String[] strings = contents.split("\\PL+");
        List<String> stringList = List.of(strings);
//        System.out.println(stringList.size());

        int count = 0;
        for (String s : stringList) {
            if (s.length() > 8) count++;
        }
        System.out.println("count = " + count);

        /*
        用Stream流实现上述单词统计
         */
        long count1 = stringList.stream().filter(s -> s.length() > 8).count();
        System.out.println("count1 = " + count1);

        /*
            用并行流 parallelStream来操作
         */
        long count2 = stringList.parallelStream().filter((string) -> string.length() > 8).count();
        System.out.println("count2 = " + count2);


    }
}