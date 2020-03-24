package com.kc345ws.blog.service.admin;


import com.kc345ws.blog.mapper.admin.AdminUserMapper;
import com.kc345ws.blog.pojo.User;
import com.kc345ws.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class AdminUserService {
    @Autowired
    private AdminUserMapper adminUserDao;

    @Transactional
    public User findByUser(String username, String password) {
        User user = adminUserDao.findByUser(username, MD5Utils.code(password));
        return user;
    }

    @Transactional
    public User findUserById(Long id) {
        return adminUserDao.findUserById(id);
    }

}
