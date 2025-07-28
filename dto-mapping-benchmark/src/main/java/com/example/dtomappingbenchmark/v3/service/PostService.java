package com.example.dtomappingbenchmark.v3.service;

import com.example.dtomappingbenchmark.v3.entity.Post;
import com.example.dtomappingbenchmark.v3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(Post post){
        return postRepository.save(post);
    }
}
