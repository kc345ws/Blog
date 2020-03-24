package com.kc345ws.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kc345ws.blog.pojo.*;
import com.kc345ws.blog.service.InitialService;
import com.kc345ws.blog.service.admin.AdminBlogService;
import com.kc345ws.blog.service.admin.AdminTypeService;
import com.kc345ws.blog.service.admin.AdminUserService;
import com.kc345ws.blog.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private AdminBlogService adminIndexService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private InitialService initialService;

    @RequestMapping("/")//Resuful风格
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model){
        //  返回主博客集
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = adminIndexService.findAllBlog();//获取所有博客
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
            //  redis访问量统计
//            b.setPageViews(initialService.getPageViews(b.getId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);//进行分页
        model.addAttribute("pages", lists);
        //  返回分类数组并带有附属博客的总数
        List<Type> types = (initialService.findAllType( initialService.findPortionType() ));
        model.addAttribute("types", types);
        //  返回标签数组并带有附属博客的总数
        List<Tag> tags = (initialService.findAllTag( initialService.findPortionTag() ));
        model.addAttribute("tags", tags);
        //  返回更新日期最新的博客
        List<Blog> blogs = initialService.findAllBlogByDate();
        model.addAttribute("blogs", blogs);
        return "index";
    }

    //  搜索博客(按标题及简介搜索)
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         Model model,@RequestParam String query) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = initialService.findAllBlogBySearch(query);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);
        return "search";
    }

    //  通过博客ID查找博客(额外通过该博客标签字符串切割，再通过名称查找对应的标签集合)+评论
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = adminIndexService.findBlogById(id);
        blog.setUser(adminUserService.findUserById(blog.getUserId()));
        blog.setTags(initialService.getTagsByNames(blog.getTagsName()));
        //  redis访问量统计
//        blog.setPageViews(initialService.setPageViews(id));

        //  单一处理MarkDown文本形成html内容,新建一个博客对象作存储避免对数据库更改
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setComtent(MarkdownUtils.markdownToHtmlExtensions(b.getComtent()));
        model.addAttribute("blog", b);

        //  通过一条博客的id查找该博客的主干评论
        List<Comment> comments = initialService.findAllCommentByTrunk(id);
        model.addAttribute("comments", comments);
        //  通过主干的id去查找有没有子评论(集合)
        Map<Integer, List<Comment>> sonComment = initialService.findSonCommentByTrunkId(comments);
        model.addAttribute("soncomment", sonComment);

        return "blog";
    }

    //  添加博客评论
    @PostMapping("/blog/submit")
    public String submitByBlogFrom(@RequestParam Long blogId, @RequestParam Long parentid,
                                   @RequestParam String atname, @RequestParam Long cid, Comment comment, HttpSession session) {
        comment.setBlogId(blogId);
        comment.setCreateTime(LocalDateTime.now());
        comment.setParentId(parentid);
        comment.setAvatar("https://unsplash.it/100/100?image=1005");

        User user = (User) session.getAttribute("user");
        initialService.addComment(comment, atname, user, cid);
        return "redirect:/blog/" + blogId;
    }
}
