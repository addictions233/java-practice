package com.one.file;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: FileDemo3 注意: File对象只是指向了某个文件或者文件夹,并没有在硬盘中创建文件
 * @Description: File类的构造方法: 1,new File(String path) 根据传入的文件路径创建File对象,
 *                                2,new File(File parent, String path)
 *                                3,new File(String parent,String son) 类似于第一个构造方法,不过文件路径拆分了
 *               File类的成员方法: 1,createNewFile() , createDir(), createDirs()   创建文件,创建目录,创建多级目录
 *                                 2,delete(), 删除文件或者文件夹, 这个删除不走回收站,无法恢复
 *                                 如果目录里面有文件或者文件夹那么删除会失败
 * @Author: one
 * @Date: 2021/07/19
 */
public class FileDemo3 {
    public static void main(String[] args) throws IOException {
        // File对象是指代路径结尾所指向的文件或者文件夹,而不是路径本身
        // 如果路径不存在, 创建file对象会抛出异常,如果file对象所指向的文件或者目录已经存在,则不会重复创建
        File file = new File("E:\\IdeaProjects\\Advanced_Code_119\\My_Day11\\src\\com\\itheima\\file\\aaa.txt");
        File file2 = new File("E:/IdeaProjects/Advanced_Code_119/My_Day11/src/com/itheima/file/bbb");
        // createNewFile()方法为创建文件路径所指向的文件
        boolean result = file.createNewFile();
        // 如果创建文件或者目录则result为true, 如果文件或者目录已经存在则result为false
        System.out.println(result);

        //创建的文件可以不指定文件后缀名, 文件后缀名只是用来指定文件类型,并不具体实际意义
        // java和linux中用 / 作为路径分隔符 \ 作为转义字符, windows中 \ 作为路径分隔符
        boolean result2 = file2.createNewFile();
        System.out.println(result2);

        File file3 = new File("E:/IdeaProjects/Advanced_Code_119/My_Day11/src/com/itheima/file/ccc/abc");
        // 创建单级目录,如果前面的目录不存在则不创建该目录
        boolean result3 = file3.mkdir();
        System.out.println(result3);
        // 创建多级目录,如果前面的目录不存在就创建前面的目录
        boolean result4 = file3.mkdirs();
        System.out.println(result4);
    }
}
