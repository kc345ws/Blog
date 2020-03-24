package com.kc345ws.blog.mapper.admin;


import com.kc345ws.blog.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserMapper {

    @Select("select * from t_user where username=#{username} and password=#{password}")
    public User findByUser(String username, String password);

    @Select("select * from t_user where id=#{id}")
    public User findUserById(Long id);

}
