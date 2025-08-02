package com.example.dtomappingbenchmark.v2.controller;

import com.example.dtomappingbenchmark.v2.dto.PostDto;
import com.example.dtomappingbenchmark.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDto dto){
        long start = System.nanoTime();
        PostDto result = postService.create(dto);
        long end = System.nanoTime();
        log.info("v2 게시글 생성 : {}ns", end - start);
        return ResponseEntity.ok(result);
    }
}