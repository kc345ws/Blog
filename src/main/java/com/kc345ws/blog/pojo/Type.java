package com.kc345ws.blog.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Type {

    private Long id;
    private String name;    //分类名称
    private long blogCount;

    private List<Blog> blogs;

}
