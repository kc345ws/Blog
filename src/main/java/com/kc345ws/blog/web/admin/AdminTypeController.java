package com.kc345ws.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kc345ws.blog.mapper.admin.AdminTypeMapper;
import com.kc345ws.blog.pojo.Type;
import com.kc345ws.blog.service.admin.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminTypeController {
    @Autowired
    private AdminTypeService adminTypeService;

    @GetMapping("/types")
    public String typeList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum , Model model){
        PageHelper.startPage(pageNum, 5);
        List<Type> types = adminTypeService.findAllType();//获取所有分类信息
        PageInfo<Type> pageInfo = new PageInfo<>(types);//分页信息
        model.addAttribute("pages",pageInfo);
        //pageNum ：当前页数
        //pageSize ：一页大小
        //调用该方法后，在此方法后面的第一个mybaits查询语句就会按照这个进行分页
        //PageInfo pageInfo=new PageInfo(shippingList);
        //则对第一次查询的集合传入，可以获得更多的页面操作信息，封装在PageInfo 这个类上
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String typeInput(){
        return "admin/types-input";
    }//return根据路径和文件名跳转

    @PostMapping("/types")//添加分类
    public String addType(String name , RedirectAttributes redirectAttributes){
        //先查询是否存在
        final Type flag = adminTypeService.findTypeByName(name);
        if(flag == null){
            Type type = new Type();
            type.setName(name);
            adminTypeService.addType(type);
            redirectAttributes.addFlashAttribute("message","添加分类成功");
            return "redirect:/admin/types";//根据mapping重定向
        }
        redirectAttributes.addFlashAttribute("message","添加失败，分类已存在");
        return "redirect:/admin/types/input";
    }

    @GetMapping("/types/{id}/delete")//删除分类
    public String deleteType(@PathVariable Long id , RedirectAttributes redirectAttributes){
        adminTypeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除分类信息成功");
        return "redirect:/admin/types";
    }

    //  修改分类列表时显示原有信息
    @GetMapping("/types/{id}/input")
    public String echoType(@PathVariable Long id, Model model) {
        model.addAttribute("type", adminTypeService.findTypeById(id));
        return "admin/types-update";
    }

    //  修改分类列表
    @PostMapping("/types/update")
    public String editType(Type type, RedirectAttributes attributes){
        adminTypeService.updateType(type.getId(), type.getName());
        attributes.addFlashAttribute("message", "修改分类成功！");
        return "redirect:/admin/types";
    }
}
