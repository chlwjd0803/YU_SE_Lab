package com.example.dtomappingbenchmark.v4.service;

import com.example.dtomappingbenchmark.v4.dto.PostDto;
import com.example.dtomappingbenchmark.v4.entity.Post;
import com.example.dtomappingbenchmark.v4.entity.User;
import com.example.dtomappingbenchmark.v4.mapper.PostMapper;
import com.example.dtomappingbenchmark.v4.repository.PostRepository;
import com.example.dtomappingbenchmark.v4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto create(PostDto dto){
        //매퍼 변환
        Post post = PostMapper.toEntity(dto);
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        PostMapper.patchUser(user, post);
        return PostMapper.toDto(postRepository.save(post));
    }
}
