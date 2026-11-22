package com.one;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 大都督周瑜
 * 微信ID: dadudu6789
 * 专注帮助程序员提升技术实力，升职涨薪，面试跳槽
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
//        model.addAttribute("username", UserController.USER_INFO.get(0).get("username"));
        return "index";
    }
}
