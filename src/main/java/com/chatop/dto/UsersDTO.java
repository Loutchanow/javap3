package com.chatop.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
    private Long id;
    private String email;
    private String name;
    private String password;


    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime created_at;
    
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updated_at;
}