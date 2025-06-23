package com.corejava.capturetongpeifu;

public class PairAlg {
    public static boolean hasNulls(Pair<?> p){
        return p.getFirst() == null || p.getSecond()== null;

    }

    public static void swap(Pair<?> p){  // 这里无限定通配符的类型变量由传入该方法的实参确定具体的类型
        /**
         *  在swap(Pair<p> p)方法中调用swaHelper()方法,这样就用T对无限定通配符?进行捕获
         */
//        ? t = p.getFirst();    // 不能这样写写无限定通配符 ?
//        p.setFirst(p.getSecond());
//        p.setSecond(t);
        swapHelper(p);

        }
    //将Pair<T>对象中的first和second变量交换位置
    private static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }
}
