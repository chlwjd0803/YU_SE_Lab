package com.example.dtomappingbenchmark.v2.service;

import com.example.dtomappingbenchmark.v1.entity.Post;
import com.example.dtomappingbenchmark.v2.dto.UserDto;
import com.example.dtomappingbenchmark.v2.entity.User;
import com.example.dtomappingbenchmark.v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostService postService;

    public UserDto create(UserDto dto) {
        User user = toEntity(dto);
        return toDto(userRepository.save(user));
    }

    public UserDto get(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return toDto(user);
    }

    // 영속성 컨텍스트가 닫히는 오류 발생
    // 반드시 @EntityGraph나 JOIN FETCH 같은 명시적 fetch 전략이 필요해. (중요 포인트)
    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .postList(user.getPosts().stream()
                        .map(postService::toDto)
                        .toList())
                .build();
    }

    public User toEntity(UserDto dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .posts(new ArrayList<>())
                .build();
    }
}