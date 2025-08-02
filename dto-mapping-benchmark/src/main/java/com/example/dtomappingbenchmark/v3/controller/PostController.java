package com.example.dtomappingbenchmark.v3.controller;

import com.example.dtomappingbenchmark.v3.dto.PostDto;
import com.example.dtomappingbenchmark.v3.service.PostService;
import com.example.dtomappingbenchmark.v3.entity.Post;
import com.example.dtomappingbenchmark.v3.entity.User;
import com.example.dtomappingbenchmark.v3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto dto) {
        long start = System.nanoTime();
        User user = userService.get(dto.getUserId()); // 유저 엔티티 조회
        Post post = toEntity(dto, user);

        Post saved = postService.create(post);
        PostDto result = toDto(saved);
        long end = System.nanoTime();
        log.info("v3 게시글 생성 : {}ns", end - start);
        return ResponseEntity.ok(result);
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
