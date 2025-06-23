package com.one.string;

/**
 * @PackageName: com.corejava.string
 * @ClassName:  StringDemo1
 * @Description: 检测字符串是否相等
 * @Author: one
 * @Date: 2020-9-4 20:23
 */
public class StringDemo1 {
    public static void main(String[] args) {
       /*
            public boolean equals(Object anObject) --> String类中 比较两个字符串是否相等
                比较两个字符串是否相等一定不要用 == 符号, ==对于引用数据类型比较是比较地址值,虽然有字符串常量池,但是用 + 或者
                subString拼接得到的字符串是不和字符串常量的字符串进行共享的,所有用 ==来判断字符串是否相等,存在严重的不确定性,
                这种不确定性是最糟糕的 bug
        */
        String str1 = "Hello";
        String str2 = "Hello";
        System.out.println(str1.equals(str2));  //输出 : true
        System.out.println(str1 == str2);      //输出 : true
        System.out.println("-----------");
        String str3 = str1.substring(0,3)+"lo";
        System.out.println(str1.equals(str3));  //输出 : true
        System.out.println(str1 == str3);      // 输出 : false

        /*
            public boolean equalsIgnoreCase(String anotherString) --> String类中的非静态方法
                不区分大小写的比较字符串是否相同
         */
    }

}
