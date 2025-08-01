package com.example.dtomappingbenchmark.v2.service;

import com.example.dtomappingbenchmark.v2.entity.Post;
import com.example.dtomappingbenchmark.v2.entity.User;
import com.example.dtomappingbenchmark.v2.repository.PostRepository;
import com.example.dtomappingbenchmark.v2.repository.UserRepository;
import com.example.dtomappingbenchmark.v2.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto create(PostDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Post post = toEntity(dto, user);
        return toDto(postRepository.save(post));
    }

    PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }

    Post toEntity(PostDto dto, User user){
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
    }

}