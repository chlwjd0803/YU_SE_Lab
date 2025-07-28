package com.example.dtomappingbenchmark.v4.service;

import com.example.dtomappingbenchmark.v4.dto.UserDto;
import com.example.dtomappingbenchmark.v4.entity.User;
import com.example.dtomappingbenchmark.v4.mapper.UserMapper;
import com.example.dtomappingbenchmark.v4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto create(UserDto dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto get(Long id) {
        return UserMapper.toDto(userRepository.findById(id).orElseThrow());
    }
}
