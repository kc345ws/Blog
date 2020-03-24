package com.kc345ws.blog.service.admin;


import com.kc345ws.blog.mapper.admin.AdminBlogMapper;
import com.kc345ws.blog.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminBlogService {

    @Autowired
    private AdminBlogMapper adminIndexDao;

    //@Autowired
    //private RedisUtil redisUtil;

    //@Transactional(rollbackFor = Exception.class)
    public void addBlog(Blog blog) {//Autowired会自动插入数据库
        adminIndexDao.addBlog(blog);
    }

    public Blog findBlogById(Long id) {
        return adminIndexDao.findBlogById(id);
    }

    public List<Blog> findAllBlog() {
        return adminIndexDao.findAllBlog();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(Blog blog) {
        adminIndexDao.updateBlog(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBlog(Long id) {
        adminIndexDao.deleteBlog(id);
        //redisUtil.expire("blog" + id, 1);
        adminIndexDao.file1();
        adminIndexDao.file2();
        adminIndexDao.file3();
    }

    public List<Blog> Search(String title, Long type) {
        if (title.trim().isEmpty()) {//查询标题去除空格后为空
            if (type != null) {
                return adminIndexDao.SearchByType(type);
            } else {//没有选择分类时，查询全部
                return adminIndexDao.findAllBlog();
            }
        } else if (type != null) {
            return adminIndexDao.SearchByTitleAndType(title, type);
        } else if (type == null){
            return adminIndexDao.SearchByTitle(title);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBlogByTypeId(Long id) {
        adminIndexDao.deleteBolgByTypeId(id);
        //redisUtil.expire("blog" + id, 1);
        adminIndexDao.file1();
        adminIndexDao.file2();
        adminIndexDao.file3();
    }

}
