package com.kc345ws.blog.service.UserServiceImpl;

import com.kc345ws.blog.mapper.UserMapper;
import com.kc345ws.blog.pojo.User;
import com.kc345ws.blog.service.UserService;
import com.kc345ws.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userRepository;
    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByNameAndPwd(username, MD5Utils.code(password));
    }
}
