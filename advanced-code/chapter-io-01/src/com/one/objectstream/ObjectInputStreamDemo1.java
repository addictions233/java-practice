package com.one.objectstream;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author one
 * 对象操作流 ObjectInputStream 作用: 将存储在文件上的类对象读取出来
 */
public class ObjectInputStreamDemo1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        method1();
//        method2();
         //已经知道了对象文件中存储一个ArrayList集合,集合中存储所有的对象
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("My_Day13/src/com/itheima/objectstream/object.txt"));
         // 由于已经知道对象文件上存储的是ArrayList<Object>,所以可以将读取的对象进行强转
        ArrayList<Object> list = (ArrayList<Object>)ois.readObject();
        Iterator<Object> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        //关流
        ois.close();
    }

    /**
     *  不知道文件中存储了几个对象,用对象操作流读取所有的对象,并对 EOFException进行捕捉
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void method2() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("My_Day13/src/com/itheima/objectstream/object.txt"));
        while(true){
            try{
                Object obj = ois.readObject();
                System.out.println(obj);
            } catch(EOFException exception){
                System.out.println("读取到对象文件末尾了");
                break;
            }
        }
        //关流
        ois.close();
    }

    /**
     *  已经知道文件中存储了几个对象,用对象操作流逐个读取对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void method1() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("My_Day13/src/com/itheima/objectstream/object.txt"));
        Object obj1 = ois.readObject();
        System.out.println("obj1 = " + obj1);
        Object obj2 = ois.readObject();
        System.out.println("obj2 = " + obj2);
        Object obj3 = ois.readObject();
        System.out.println("obj3 = " + obj3);
        Object obj4 = ois.readObject();
        System.out.println("obj4 = " + obj4);
        /**
         *  obj5原本读到是 Student学生对象, 当我们对Student对象进行修改时,这里读的时候会抛出:java.io.InvalidClassException
         *  如果我们将Student类添加 静态常量序列号,就不会报出这个异常 private static final long serialVersionUID = 3011428894624610727L;
         */
        Object obj5 = ois.readObject();
        System.out.println("obj5 = " + obj5);
        /**
         * 当到达文件读取对象的末尾时,没有读到对象时会抛出:java.io.EOFException
         */
//        Object obj6 = ois.readObject();
        //关流
        ois.close();
    }
}
