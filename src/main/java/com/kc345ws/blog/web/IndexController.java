package com.kc345ws.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")//Resuful风格
    public String index(){
        //System.out.println("--index--"+"id="+id+" name=" + name);
        return "indextmp";
    }
}
