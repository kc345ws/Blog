package com.kc345ws.blog.web.admin;

import com.kc345ws.blog.pojo.User;
import com.kc345ws.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    UserService userService;
    //跳转到登陆页面
    @RequestMapping//请求页面为get方法
    public String loginPage(){
        return "admin/login";
    }

    @RequestMapping("/login")//表单method为post action为/admin/login
    public String login(
            @RequestParam String username,//@RequestParam表名是必须传递的参数
            @RequestParam String password,
            HttpSession sessions,
            RedirectAttributes redirectAttributes) {
        //用于重定向之后还能带参数跳转的的工具类
        final User user = userService.checkUser(username, password);
        if(user!=null){
            user.setPassword(null);//将密码设为空，为了安全性
            sessions.setAttribute("user",user);
            return "admin/index";
        }
        redirectAttributes.addFlashAttribute("message","用户名或密码错误");
        // 这种方法是隐藏了参数，链接地址上不直接暴露，但是能且只能在重定向的 “页面” 获取prama参数值。
        return "redirect:/admin";//用户不存在或密码错误重定向到登陆页面
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/admin";
    }

    @RequestMapping("/index")
    public String adminIndex(){
        return "admin/index";
    }
}
