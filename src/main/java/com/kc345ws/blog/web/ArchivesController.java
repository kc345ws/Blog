package com.kc345ws.blog.web;


import com.kc345ws.blog.pojo.Blog;
import com.kc345ws.blog.service.admin.AdminBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArchivesController {

    @Autowired
    private AdminBlogService adminIndexService;

    @GetMapping("/archives")
    public String Archives(Model model) {
        List<Blog> blogs = adminIndexService.findAllBlog();
        model.addAttribute("blogs", blogs);
        return "archives";
    }

}
