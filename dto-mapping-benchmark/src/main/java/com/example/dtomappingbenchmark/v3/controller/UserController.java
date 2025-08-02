package com.example.dtomappingbenchmark.v3.controller;

import com.example.dtomappingbenchmark.v3.dto.PostDto;
import com.example.dtomappingbenchmark.v3.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.dtomappingbenchmark.v3.dto.UserDto;
import com.example.dtomappingbenchmark.v3.entity.User;
import com.example.dtomappingbenchmark.v3.service.UserService;


import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        User user = toEntity(dto);
        UserDto saved = toDto(userService.create(user));
        return ResponseEntity.ok(saved);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        UserDto userDto = toDto(userService.get(id));
        return ResponseEntity.ok(userDto);
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPosts(dto.getPostList().stream()
                .map(p -> {
                    var post = new Post();
                    post.setId(p.getId());
                    post.setTitle(p.getTitle());
                    post.setContent(p.getContent());
                    post.setUser(user); // 양방향 연관관계 설정
                    return post;
                }).collect(Collectors.toList()));
        return user;
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .postList(user.getPosts().stream()
                        .map(p -> new PostDto(
                                p.getId(), p.getTitle(), p.getContent(), user.getId()))
                        .collect(Collectors.toList()))
                .build();
    }
}


