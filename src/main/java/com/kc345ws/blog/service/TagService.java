package com.kc345ws.blog.service;

import com.kc345ws.blog.mapper.TagMapper;
import com.kc345ws.blog.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagDao;


    @Transactional
    public List<Blog> findAllBlogByTagName(String name) {
        return tagDao.findAllBlogByTagName(name);
    }

}
