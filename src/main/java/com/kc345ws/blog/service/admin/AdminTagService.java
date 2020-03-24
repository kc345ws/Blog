package com.kc345ws.blog.service.admin;

import com.kc345ws.blog.mapper.admin.AdminTagMapper;
import com.kc345ws.blog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service("TagService")
public class AdminTagService {

    @Autowired
    private AdminTagMapper adminTagDao;

    @Transactional
    public void addTag(Tag tag)  {
        adminTagDao.addTag(tag);
    }

    @Transactional
    public Tag findTagById(Long id){
        return adminTagDao.findTagById(id);
    }

    @Transactional
    public Tag findTagByName(String name){
        return adminTagDao.findTagByName(name);
    }

    @Transactional
    public List<Tag> findAllTag(){
        List<Tag> Tags = adminTagDao.findAllTag();
        return Tags;
    }

    @Transactional
    public void updateTag(Long id, String name){
        adminTagDao.updateTag(id, name);
    }

    @Transactional
    public void deleteTag(Long id){
        adminTagDao.deleteTag(id);
        adminTagDao.file1();
        adminTagDao.file2();
        adminTagDao.file3();
    }

}
