package com.example.dtomappingbenchmark.v4.mapper;

import com.example.dtomappingbenchmark.v4.entity.User;
import com.example.dtomappingbenchmark.v4.dto.UserDto;

import java.util.ArrayList;

public class UserMapper {
    public static User toEntity(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .posts(new ArrayList<>())
                .build();
    }

    public static UserDto toDto(User entity){
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .postList(entity.getPosts().stream()
                        .map(PostMapper::toDto)
                        .toList())
                .build();
    }
}
