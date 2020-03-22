package com.kc345ws.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kc345ws.blog.mapper.admin.AdminTypeMapper;
import com.kc345ws.blog.pojo.Type;
import com.kc345ws.blog.service.admin.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private AdminTypeService adminTypeService;

    @RequestMapping("/types")
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

    @RequestMapping("/types/input")
    public String typeInput(){
        return "types-input";
    }

}
