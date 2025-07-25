package com.example.dtomappingbenchmark.v2.dto;

import com.example.dtomappingbenchmark.v1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<PostDto> postList = new ArrayList<>();

    public User toEntity(){
        return User.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .posts(new ArrayList<>())
                .build();
    }
}
