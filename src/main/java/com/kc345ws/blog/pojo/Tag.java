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
public class Tag {

    private Long id;
    private String name;    //标签名称

    private Long blogCount;

    private List<Blog> blogs;

}
