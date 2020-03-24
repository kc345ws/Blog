package com.kc345ws.blog.mapper;


import com.kc345ws.blog.pojo.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMapper {

    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> findAllBlogByTypeId(Long id);
}
