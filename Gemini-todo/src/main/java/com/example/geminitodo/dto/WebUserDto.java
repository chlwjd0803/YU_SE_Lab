package com.example.geminitodo.dto;

import com.example.geminitodo.domain.WebUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebUserDto {
    private String username;
    private String password;
    private String email;

    public WebUser toEntity() {
        return WebUser.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
