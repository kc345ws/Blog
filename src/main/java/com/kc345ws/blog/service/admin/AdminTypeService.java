package com.kc345ws.blog.service.admin;

import com.kc345ws.blog.mapper.admin.AdminTypeMapper;
import com.kc345ws.blog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class AdminTypeService {
    @Autowired
    private AdminTypeMapper adminTypeMapper;

    @Transactional
    public void addType(Type type)  {
        adminTypeMapper.addType(type);
    }

    @Transactional
    public Type findTypeById(Long id){
        return adminTypeMapper.findTypeById(id);
    }

    @Transactional
    public Type findTypeByName(String name){
        return adminTypeMapper.findTypeByName(name);
    }

    @Transactional
    public List<Type> findAllType(){
        List<Type> types = adminTypeMapper.findAllType();
        return types;
    }

    @Transactional
    public void updateType(Long id, String name){
        adminTypeMapper.updateType(id, name);
    }

    @Transactional
    public void deleteType(Long id){
        adminTypeMapper.deleteType(id);
        adminTypeMapper.file1();
        adminTypeMapper.file2();
        adminTypeMapper.file3();
    }
}
