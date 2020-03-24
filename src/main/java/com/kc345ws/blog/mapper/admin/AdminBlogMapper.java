package com.kc345ws.blog.mapper.admin;


import com.kc345ws.blog.pojo.Blog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminBlogMapper {

    @Select("select * from t_blog where id=#{id}")
    Blog findBlogById(Long id);

    @Insert("insert into t_blog(photo,title,comtent,flag,opengood,openstatement,opencomment,recommend,create_time,update_time,user_id,type_id,tags_name,description)" +
            " values(#{photo}, #{title}, #{comtent}, #{flag},#{opengood},#{openstatement},#{opencomment},#{recommend}," +
            " #{createTime}, #{updateTime}, #{userId}, #{typeId}, #{tagsName}, #{description})")
    void addBlog(Blog blog);//通过反射获取属性名和属性值


    /*id作为当前@Results注解的唯一标识很好理解
    * @Results的基本用法。当数据库字段名与实体类对应的属性名不一致时，
    * 可以使用@Results映射来将其对应起来。column为数据库字段名，
    * porperty为实体类属性名，jdbcType为数据库字段数据类型，id为是否为主键。
    * one和many用于关联查询。比如上面列子中提到的Group类中包含属性List<User> userList，
    * 这时集合类型数据，与Group是一对多的关系，需要用many配置，
    * 如果Group中需要对象类型数据User user，那么就需要使用one进行配置
    * */
    @Select("select * from t_blog")
    @Results(id = "Map", value = {
            @Result(column = "id", property = "id"),
            @Result(property = "type", column = "id",//博客与分类一对一,colnum为表中属性名
                    one = @One(select = "com.kc345ws.blog.mapper.admin.AdminTypeMapper.findTypeById", fetchType = FetchType.LAZY)),
            @Result(property = "user", column = "id",
                    one = @One(select = "com.kc345ws.blog.mapper.admin.AdminUserMapper.findUserById", fetchType = FetchType.LAZY))
    })
    List<Blog> findAllBlog();

    @Update("update t_blog set photo=#{photo},title=#{title},comtent=#{comtent},flag=#{flag},opengood=#{opengood},openstatement=#{openstatement},opencomment=#{opencomment}," +
            "recommend=#{recommend},update_time=#{updateTime},type_id=#{typeId},tags_name=#{tagsName},description=#{description} where id=#{id}")
    void updateBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    void deleteBlog(Long id);

    @Delete("delete from t_blog where type_id=#{id}")
    void deleteBolgByTypeId(Long id);

    @Select("select * from t_blog where title like CONCAT('%',#{title},'%') and type_id=#{id}")
    List<Blog> SearchByTitleAndType(String title, Long id);

    @Select("select * from t_blog where title like CONCAT('%',#{title},'%')")
    List<Blog> SearchByTitle(String title);

    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> SearchByType(Long id);

    @Update("alter table t_blog drop id")
    void file1();
    @Update("alter table t_blog add id int(3) first")
    void file2();
    @Update("alter table t_blog modify column id int(3) not null auto_increment,add primary key(id)")
    void file3();
}
