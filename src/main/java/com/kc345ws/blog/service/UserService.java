package com.kc345ws.blog.service;

import com.kc345ws.blog.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User checkUser(String username,String password);//检查用户名和密码是否正确
}
