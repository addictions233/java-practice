package com.one.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.one.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: pojo类
 * @Author: one
 * @Date: 2021/05/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Integer age;
    /**
     * fastjson注解: 使用toString()方法将枚举类转换为json
     * 解决枚举类转为json时的取值问题
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private SexEnum sex;

    private Date birthday;
    private String address;
}
