package com.example.dtomappingbenchmark.v2.controller;

import com.example.dtomappingbenchmark.v2.dto.PostDto;
import com.example.dtomappingbenchmark.v2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDto dto){
        return ResponseEntity.ok(postService.create(dto));
    }
}