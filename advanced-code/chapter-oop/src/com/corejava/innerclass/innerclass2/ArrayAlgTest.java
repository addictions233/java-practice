package com.corejava.innerclass.innerclass2;

public class ArrayAlgTest {
    public static void main(String[] args) {
        double[] array = new double[10];
          // 错误写法
//        for (double v : array) {    //迭代器获取的是目标数组的拷贝,而java中只有值传递,修改拷贝对象不能修改原对象
//            v = Math.random()*100;
//        }
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.random()*100;
        }
//        System.out.println(Arrays.toString(array));
        ArrayAlg.Pair pair = ArrayAlg.minmax(array);
        System.out.println("min = "+pair.getFirst());
        System.out.println("max = "+ pair.getSecond());

    }
}
