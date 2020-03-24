package com.kc345ws.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kc345ws.blog.pojo.Blog;
import com.kc345ws.blog.pojo.Type;
import com.kc345ws.blog.pojo.User;
import com.kc345ws.blog.service.admin.AdminBlogService;
import com.kc345ws.blog.service.admin.AdminTagService;
import com.kc345ws.blog.service.admin.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBlogController {
    @Autowired
    private AdminBlogService adminIndexService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminTagService adminTagService;

    @RequestMapping("/blogs")
    public String blog(){
        return "admin/blogs";
    }

    @GetMapping("/index")//访问首页，博客分页
    public String backIndex(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = adminIndexService.findAllBlog();
//        for (Blog b : allBlog) {
//            b.setType(adminTypeService.findTypeById(b.getTypeId()));
//        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("types", adminTypeService.findAllType());
        model.addAttribute("pages", lists);
        return "admin/index";
    }

    //  搜索博客
    @PostMapping(value = "/search")
    public String searchBlog(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                             Model model,@RequestParam String title,@RequestParam Long type) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> blogs = adminIndexService.Search(title, type);
        for (Blog b : blogs) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));//通过分类ID获取分类
        }
        PageInfo<Blog> lists = new PageInfo<>(blogs);
        model.addAttribute("pages", lists);

        List<Type> types = adminTypeService.findAllType();
        model.addAttribute("types", types);
        return "admin/index";
    }

    //  回主页时加载博客列表
    @GetMapping("/input")
    public String input(Model model) {
        model.addAttribute("types", adminTypeService.findAllType());
        model.addAttribute("tags", adminTagService.findAllTag());
        return "admin/blogs";
    }

      //添加博客
    @PostMapping("/input")
    public String addBlog(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");

        blog.setCreateTime(LocalDateTime.now());
        blog.setUpdateTime(LocalDateTime.now());
        blog.setUserId(user.getId());
        blog.setTypeId(blog.getType().getId());
        if(blog.getTypeId() == null) blog.setTypeId(14L);
        if(blog.getTagsName()== null|| blog.getTagsName()=="")blog.setTagsName("默认标签");

        adminIndexService.addBlog(blog);
        attributes.addFlashAttribute("message", "添加博客成功！");

        return "redirect:/admin/index";
    }

    //  回显博客
    @GetMapping("blogs/{id}/input")
    public String echoBlog(@PathVariable Long id, Model model) {
        Blog blog = adminIndexService.findBlogById(id);
        model.addAttribute("types", adminTypeService.findAllType());
        model.addAttribute("tags", adminTagService.findAllTag());
        model.addAttribute("blog", blog);

        return "admin/blogs-update";
    }

    //  修改博客
    @PostMapping("blogs/update")
    public String editBlog(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");

        blog.setUpdateTime(LocalDateTime.now());
//        blog.setUpdate_time(LocalDateTime.now());
        blog.setTypeId(blog.getType().getId());
        if(blog.getTypeId() == null) blog.setTypeId(14L);
        if(blog.getTagsName()== null || blog.getTagsName()=="")blog.setTagsName("默认标签");
        blog.setUserId(user.getId());

        adminIndexService.updateBlog(blog);
        attributes.addFlashAttribute("message", "修改博客成功！");
        return "redirect:/admin/index";
    }

    //  删除标签列表
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        adminIndexService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除博客成功！");
        return "redirect:/admin/index";
    }

    public boolean checkinput(User user,Blog blog){
        if(user == null){
            return false;
        }else if(blog.getType().getId() == null){
            return false;
        }
        return true;
    }
}
