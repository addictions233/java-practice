package com.one.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyDateConverter
 * @Description: 自定义日期类型转换器, 注意:自己可以定义任何类型的类型转换器,将前端页面传递
 *                过来的数据转换为自己需要的java数据类型,所以该功能很强大
 * @Author: one
 * @Date: 2020/12/06
 */
public class MyDateConverter  implements Converter<String,Date> {
    /**
     * 通常前端页面传递过来都是String字符串类型,然后我们在本方法类需要
     * 将字符串类型转换为我们需要的java数据类型
     * @param source
     * @return
     */
    @Override
    public Date convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 父类中的convert没有抛出异常,子类重写的方法一定不能抛出异常
        Date date = null;
        try {
            date = format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
