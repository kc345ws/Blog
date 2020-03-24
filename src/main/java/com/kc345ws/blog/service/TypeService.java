package com.kc345ws.blog.service;

import com.kc345ws.blog.mapper.TypeMapper;
import com.kc345ws.blog.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeMapper typeDao;

    @Transactional
    public List<Blog> findAllBlogByTypeId(Long id) {
        return typeDao.findAllBlogByTypeId(id);
    }
}
