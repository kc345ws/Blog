package com.kc345ws.blog.mapper;

import com.kc345ws.blog.pojo.Blog;
import com.kc345ws.blog.pojo.Comment;
import com.kc345ws.blog.pojo.Tag;
import com.kc345ws.blog.pojo.Type;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//主页初始化Mapper
public interface InitialMapper {

    @Select("select count(*) from t_blog where type_id=#{id}")
    Long findBlogCountByTypeId(Long id);

    @Select("select * from t_tag LIMIT 0,8")
    List<Tag> findPortionTag();

    @Select("select count(*) from t_blog where tags_name like CONCAT('%',#{name},'%')")
    Long findAllBlogByTagName(String name);

    @Select("select * from t_blog order by update_time DESC LIMIT 0,6")
    List<Blog> findAllBlogByDate();

    @Select("select * from t_type LIMIT 0,6")
    List<Type> findPortionType();

    @Select("select * from t_blog where title like CONCAT('%',#{query},'%') or description like CONCAT('%',#{query},'%')")
    List<Blog> findAllBlogBySearch(String query);

    @Select("select * from t_comment_prt where blog_id=#{id} order by create_time DESC")
    List<Comment> findAllCommentByTrunk(Long id);

    @Select("select * from t_comment_son where parent_id=#{id} order by create_time DESC")
    List<Comment> findSonCommentByTrunkId(Long id);

    @Insert("insert into t_comment_prt values(null,#{username}, #{email}, #{comtent}, #{avatar}, #{createTime}, #{blogId},#{parentId},#{sonId})")
    void addComment(Comment comment);

    @Insert("insert into t_comment_son values(null,#{username}, #{email}, #{comtent}, #{avatar}, #{createTime}, #{blogId},#{parentId},#{sonId})")
    void addCommentByadmin(Comment comment);

    @Select("select son_id from t_comment_prt where id=#{parentId}")
    Long findCommentByParentId(Long parentId);

    @Update("update t_comment_prt set son_id=#{id} where id=#{id}")
    void setCommentBySonId(Long id);

}
