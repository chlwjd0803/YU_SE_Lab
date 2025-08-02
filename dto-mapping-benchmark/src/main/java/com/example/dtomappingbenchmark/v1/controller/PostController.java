package com.example.dtomappingbenchmark.v1.controller;

import com.example.dtomappingbenchmark.v1.dto.PostDto;
import com.example.dtomappingbenchmark.v1.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDto dto){
        long start = System.nanoTime();
        PostDto result = postService.create(dto);
        long end = System.nanoTime();
        log.info("v1 게시글 생성 : {}ms", end - start);
        return ResponseEntity.ok(result);
    }
}
