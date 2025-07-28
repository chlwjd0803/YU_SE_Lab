package com.example.dtomappingbenchmark.v4.mapper;

import com.example.dtomappingbenchmark.v4.entity.Post;
import com.example.dtomappingbenchmark.v4.entity.User;
import com.example.dtomappingbenchmark.v4.dto.PostDto;

public class PostMapper {
    public static Post toEntity(PostDto dto){
        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(null)
                .build();
    }

    public static void patchUser(User user, Post post){
        post.setUser(user);
    }

    public static PostDto toDto(Post entity){
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .userId(entity.getUser().getId())
                .build();
    }
}
