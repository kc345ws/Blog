package com.kc345ws.blog.web;

import com.kc345ws.blog.pojo.Blog;
import com.kc345ws.blog.pojo.Type;
import com.kc345ws.blog.service.InitialService;
import com.kc345ws.blog.service.TypeService;
import com.kc345ws.blog.service.admin.AdminBlogService;
import com.kc345ws.blog.service.admin.AdminTypeService;
import com.kc345ws.blog.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeController {

    @Autowired
    private AdminBlogService adminIndexService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private InitialService initialService;

    @Autowired
    private TypeService typeService;

    //  进入分类主页面
    @GetMapping("/type")
    public String AllBlogByType(Model model) {
        List<Blog> allBlog = adminIndexService.findAllBlog();
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        model.addAttribute("pages", allBlog);
        List<Type> types = (initialService.findAllType( initialService.findPortionType() ));
        model.addAttribute("types", types);

        return "type";
    }

    //  由链接进入分页页
    @GetMapping("/type/{id}")
    public String BlogByType(Model model, @PathVariable Long id) {
        List<Blog> allBlog = typeService.findAllBlogByTypeId(id);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        model.addAttribute("pages", allBlog);

        List<Type> types = (initialService.findAllType(adminTypeService.findAllType()));
        model.addAttribute("types", types);
        return "type";
    }
}
