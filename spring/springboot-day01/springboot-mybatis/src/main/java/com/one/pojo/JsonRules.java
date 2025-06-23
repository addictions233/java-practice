package com.one.pojo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @ClassName: JsonRules
 * @Description: 与tb_json_rules表相映射的实体类
 * @Author: one
 * @Date: 2022/01/25
 */
@Data
public class JsonRules {
    private Integer id;

    private String sysName;

    /**
     * 与Mysql的json数据类型的字段相映射
     */
    private JSONArray rules;

    private String creationDate;
}
