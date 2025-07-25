package com.example.dtomappingbenchmark.v1.service;

import com.example.dtomappingbenchmark.v1.dto.UserDto;
import com.example.dtomappingbenchmark.v1.entity.User;
import com.example.dtomappingbenchmark.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserDto create(UserDto dto){
        User entity = dto.toEntity();
        User saved = userRepository.save(entity);
        return saved.toDto();
    }

    public UserDto get(Long id) {
        return userRepository.findById(id).orElseThrow().toDto();
    }
}
