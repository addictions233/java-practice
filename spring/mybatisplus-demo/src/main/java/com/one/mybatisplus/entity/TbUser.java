package com.one.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.one.mybatisplus.enums.UserStatusEnum;
import lombok.Data;

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
@TableName("tb_user3") //mybatis plus注解,解决pojo类名和数据表名不一致
public class TbUser {
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
}
