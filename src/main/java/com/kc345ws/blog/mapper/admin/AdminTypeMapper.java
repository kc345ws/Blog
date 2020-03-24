package com.kc345ws.blog.mapper.admin;

import com.kc345ws.blog.pojo.Type;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTypeMapper {

    @Insert("insert into t_type(name) values(#{name})")
    void addType(Type type);

    @Select("select * from t_type where id = #{id}")
    Type findTypeById(Long id);

    @Select("select * from t_type where name = #{name}")
    Type findTypeByName(String name);

    @Select("select * from t_type")
    List<Type> findAllType();

    @Update("update t_type set name = #{name} where id = #{id}")
    void updateType(Long id, String name);

    @Delete("delete from t_type where id = #{id}")
    void deleteType(Long id);

    //一起删除相关博客
    @Update("alter table t_type drop id")
    void file1();
    @Update("alter table t_type add id int(3) first")
    void file2();
    @Update("alter table t_type modify column id int(3) not null auto_increment,add primary key(id)")
    void file3();
}
