package com.kc345ws.blog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;

    @Email
    private String email;
    private String avatar;//头像

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private LocalDateTime creaTetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private LocalDateTime updaTetime;

    private List<Blog> blogs;


}
