package com.ims.backend.controller; // 注意包名要对应

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 告诉Spring：这是一个接收Web请求的控制器
public class HelloController {

    @GetMapping("/hello") // 告诉Spring：如果访问GET请求 /hello，就执行下面方法
    public String sayHello() {
        return "Hello, Spring Boot! 这是我的第一个接口。";
    }
}