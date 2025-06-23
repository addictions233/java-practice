package com.one.objectstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author one
 *  对象操作流 ObjectInputSteam和 ObjectOutputStream 对象输入输出流还是字节流,是对基本字节流的一种封装
 *      ObjectOutputStream 作用: 将实现了序列化的对象直接写到文件当中
 *      注意: 能被对象输出流操作的对象类型必须实现Serializable序列化接口
 */
public class ObjectOutputStreamDemo1 {
    public static void main(String[] args) throws IOException {
//        method1();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("My_Day13\\src\\com\\itheima\\objectstream\\object.txt"));
        //创建一个泛型为Object类型的集合对象
        ArrayList<Object> list = new ArrayList<>();
        list.add(100);
        list.add("hello");
        list.add(new int[3]);
        list.add(new LinkedList<>());
        list.add(new Student("张三","23","男"));
        //将list集合对象写入到文件中
        oos.writeObject(list);
        //关流
        oos.close();
    }

    /**
     *  这样将每个对象逐个写到文件中读取的时候很不方便,通常将所有的对象都存储在集合(例如 ArrayList<Object>)当中,
     *  这样将对象写到文件中只用写一次,就写集合对象,从文件中读取对象时也很方便,就读取一个集合对象
     * @throws IOException
     */
    private static void method1() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("My_Day13\\src\\com\\itheima\\objectstream\\object.txt"));
        oos.writeObject(100);
        oos.writeObject("hello");
        oos.writeObject(new int[3]);
        oos.writeObject(new ArrayList());
       /**
        *   如果oos写出的对象的所属类没有实现 Serializable 接口就会抛出
        *   NotSerializableException  没有序列化异常
        */
        oos.writeObject(new Student());

        //关流
        oos.close();
    }
}
