package com.one.nacos.controller;

import com.one.nacos.pojo.Phone;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PhoneController
 * @Description: controller层
 * @Author: one
 * @Date: 2021/03/04
 */
@RestController
@RequestMapping("/provider")
@RefreshScope  //该注解是,nacos配置中心配置文件修改之后,自动获取修改之后的配置
public class PhoneController {
    @GetMapping("/phone/{id}")
    public Object getPhone(@PathVariable("id") Integer id){

        return new Phone(id,"华为手机",9999.00,"拍照无敌");
    }
}
