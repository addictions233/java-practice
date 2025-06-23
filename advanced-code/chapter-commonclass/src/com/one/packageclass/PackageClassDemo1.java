package com.one.packageclass;

public class PackageClassDemo1 {
    public static void main(String[] args) {
        //将字符串的字面值转换为基本数据类型
        byte a = Byte.parseByte("100");
        short b = Short.parseShort("200");
        int c = Integer.parseInt("300");
        double d = Double.parseDouble("1.0");
        boolean bool = Boolean.parseBoolean("true");
    }
}
