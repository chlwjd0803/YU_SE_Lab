package com.example.dtomappingbenchmark.v4.controller;

import com.example.dtomappingbenchmark.v4.dto.PostDto;
import com.example.dtomappingbenchmark.v4.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDto dto){
        return ResponseEntity.ok(postService.create(dto));
    }
}
