package com.luckysite.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @aothor dongyingjie
 * @date 2020/2/25  15:03
 **/
@Controller
public class IndexController {

    @GetMapping("/")
    public  String index(){
        return "index";
    }
}
