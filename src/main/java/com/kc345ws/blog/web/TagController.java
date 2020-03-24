package com.kc345ws.blog.web;

import com.kc345ws.blog.pojo.Blog;
import com.kc345ws.blog.pojo.Tag;
import com.kc345ws.blog.service.InitialService;
import com.kc345ws.blog.service.TagService;
import com.kc345ws.blog.service.admin.AdminBlogService;
import com.kc345ws.blog.service.admin.AdminTagService;
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
public class TagController {

    @Autowired
    private AdminBlogService adminIndexService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private InitialService initialService;

    @Autowired
    private AdminTagService adminTagService;

    @Autowired
    private TagService tagService;


    //  标签页
    @GetMapping("/tag")
    public String AllBlogByTag(Model model) {
        List<Blog> allBlog = adminIndexService.findAllBlog();
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
            b.setTags(initialService.getTagsByNames(b.getTagsName()));
        }
        model.addAttribute("pages", allBlog);

        List<Tag> tags = (initialService.findAllTag( initialService.findPortionTag() ));
        model.addAttribute("tags", tags);

        return "tag";
    }

    //  由链接进入标签页
    @GetMapping("/tag/{name}")
    public String BlogByTag(Model model, @PathVariable String name) {
        List<Blog> allBlog = tagService.findAllBlogByTagName(name);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
            b.setTags(initialService.getTagsByNames(b.getTagsName()));
        }
        model.addAttribute("pages", allBlog);

        List<Tag> tags = initialService.findAllTag(adminTagService.findAllTag());
        model.addAttribute("tags", tags);
        return "tag";
    }
}
