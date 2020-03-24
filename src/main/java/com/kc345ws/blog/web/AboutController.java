package com.kc345ws.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutController {


    @GetMapping("/about")
    public String AboutMe() {
        return "about";
    }

}
