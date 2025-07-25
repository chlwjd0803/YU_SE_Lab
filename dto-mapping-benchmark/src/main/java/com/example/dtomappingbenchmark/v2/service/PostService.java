package com.example.dtomappingbenchmark.v2.service;

import com.example.dtomappingbenchmark.v1.dto.PostDto;
import com.example.dtomappingbenchmark.v1.entity.Post;
import com.example.dtomappingbenchmark.v1.entity.User;
import com.example.dtomappingbenchmark.v1.repository.PostRepository;
import com.example.dtomappingbenchmark.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto create(PostDto dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(); // 해당 유저가 있는지만 검사
        Post post = dto.toEntity();
        post.setUser(user);
        Post saved = postRepository.save(post);
        return saved.toDto();
    }

}
