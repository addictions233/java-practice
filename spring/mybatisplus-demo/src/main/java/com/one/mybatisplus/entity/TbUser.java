package com.one.mybatisplus.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.one.mybatisplus.config.EnumJsonSerializer;
import com.one.mybatisplus.enums.UserStatusEnum;
import com.one.mybatisplus.handler.JsonbTypeHandler;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: 实体类
 *     使用mybatis plus能让pojo属性和数据表的字段进行自动映射的条件:
 *       1,pojo类的属性名称和数据表的字段名称一样
 *       2,pojo类的属性名称采用驼峰命名规则(userName),数据表中字段用下划线(user_name)
 *    如果不满足上面两种情况,那么就要用 @TableField注解来进行配置
 * @Author: one
 * @Date: 2020/12/23
 */
@Data
@NoArgsConstructor
@TableName(value = "tb_user3", autoResultMap = true) //mybatis plus注解,解决pojo类名和数据表名不一致
// autoResultMap = true 开启自动映射
public class TbUser extends Model<TbUser> {
    /**
     * 设置id生成策略,AUTO数据表字段自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * /解决类的属性名和表的字段名无法映射问题
     */
    @TableField("user_name")
    private String userName;

    /**
     *  优雅的使用枚举
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private UserStatusEnum userStatus;

    private String password;

    private String name;

    private Integer age;

    private String email;

    /**
     * 当pojo对象映射添加到数据表中时不添加该属性
     */
    @TableField(exist = false)
    private String info;

    /**
     * 扩展字段,存储用户的其他信息,如手机号,地址等, 对应数据库中的json类型
     *
     */
//    @TableField(typeHandler = FastjsonTypeHandler.class) // 使用mybatis plus的fastjson类型处理器
    @TableField(typeHandler = JsonbTypeHandler.class) // 使用自定义的类型处理器
    private JSONObject extend;

    public TbUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
