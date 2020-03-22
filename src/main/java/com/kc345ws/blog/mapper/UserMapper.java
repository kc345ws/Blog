package com.kc345ws.blog.mapper;

import com.kc345ws.blog.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("select * from t_user where username = #{username} and password=#{password}")
    User findByNameAndPwd(String username , String password);
}
