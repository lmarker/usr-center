package com.maksdu.usr.center.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biz")
public class BizController {

    @RequestMapping("/home")
    public String home() {
        return "hello world";
    }


}
