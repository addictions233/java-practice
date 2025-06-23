package com.one.xmlparse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用的XML解析方式： DOM解析  SAX解析  JDOM解析  DOM4J解析
 * DOM4J性能最好，连Sun的JAXM也在用DOM4J。目前许多开源项目中大量采用DOM4J，
 *         例如大名鼎鼎的Hibernate也用DOM4J来读取XML配置文件。如果不考虑可移植性，那就采用DOM4J。
 * JDOM和DOM在性能测试时表现不佳，在测试10M文档时内存溢出。
 *       在小文档情况下还值得考虑使用DOM和JDOM。虽然JDOM的开发者已经说明他们期望在正式发行版前专注性能问题，
 *       但是从性能观点来看，它确实没有值得推荐之处。另外，DOM仍是一个非常好的选择。
 *       DOM实现广泛应用于多种编程语言。它还是许多其它与XML相关的标准的基础，
 *       因为它正式获得W3C推荐（与基于非标准的Java模型相对），所以在某些类型的项目中可能也需要它（如在JavaScript中使用DOM）。
 * SAX表现较好，这要依赖于它特定的解析方式－事件驱动。一个SAX检测即将到来的XML流，
 *      但并没有载入到内存（当然当XML流被读入时，会有部分文档暂时隐藏在内存中）
 *
 * @author one
 */
public class StudentXmlParse {
    /**
     * 利用 dom4j解析xml文件
     * @param args args
     * @throws DocumentException
     */
    public static void main(String[] args) throws DocumentException {
        SAXReader reader = new SAXReader();
        // 1,从xml文件路径读取类Document对象 文件对象
        Document document = reader.read("My_Day17\\xml\\student.xml");
        // 2, 从文件对象中获取根标签 标签类型都是 Element
        Element rootElement = document.getRootElement();

        // 3, 根标签通过标签名"student"获取所有的student标签  类型为 Element
        List<Element> students = rootElement.elements("student");
//        System.out.println(students.size()); // 输出 2

        ArrayList<Student> list = new ArrayList<>();
        // 遍历所有的学生标签对象
        for (Element student : students) {
            // 学生标签获取属性
            Attribute id = student.attribute("id");
            //通过属性对象获取属性值
            String idValue = id.getValue();

            // 学生标签通过标签名"name"获取name标签
            Element name = student.element("name");
            // 通过name变迁对象获取标签值 返回String类型
            String nameText = name.getText();

            // 学生标签通过标签名"age"获取age标签
            Element age = student.element("age");
            //通过age标签获取标签值 返回String类型
            String ageText = age.getText();

            list.add(new Student(idValue,nameText,Integer.parseInt(ageText)));
        }

        System.out.println("list = " + list);
    }
}
