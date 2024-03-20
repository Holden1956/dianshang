package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:xuanhe
 * @date:2024/2/27 1:07
 * @modyified By:
 */
@Controller // 可以返回视图
// 自动为springboot应用进行配置
//@EnableAutoConfiguration
public class HelloController {

    @RequestMapping("/myIndex")
    public String index() {
        System.out.println("第一个springboot");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("跳转到登录页面");
        return "web/login";
    }
}

