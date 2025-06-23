package com.one.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author one
 * @description TODO
 * @date 2023-6-3
 */
@Data
public class SystemUser {
    @Size(max = 10, message = "最大长度不能超过10")
    private String name;

    private Integer age;

    private String address;

    @NotBlank(message = "性别不能为空")
    private String gender;
}
