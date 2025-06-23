package com.one.easyexcel.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName: TbUser
 * @Description: 实体类
 * @Author: one
 * @Date: 2021/06/05
 */
@Data
public class TbUser {
    /**
     * 注解@ExcelProperty表示: 当前属性对应的excel表格中的表头字段
     * value属性值表示表头字段的名称,order属性值表示表头字段的排序, index表示标题所对应的下标
     */
    @ExcelProperty(value = "ID",order = 0, index = 0)
    private Integer id;
    @ExcelProperty(value = "用户名",order = 1, index = 1)
    private String userName;
    @ExcelProperty(value = "密码",order = 2, index = 2)
    private String password;
    @ExcelProperty(value = "姓名",order = 3, index = 3)
    private String name;
    @ExcelProperty(value = "年龄",order = 4, index = 4)
    private Integer age;
    @ExcelProperty(value = "邮箱",order = 5, index = 5)
    private String email;
    /**
     * 注解 @ExcelIgnore 表示忽略该属性,不转为excel中的列
     */
//    @ExcelIgnore
//    private Integer gender;

}
