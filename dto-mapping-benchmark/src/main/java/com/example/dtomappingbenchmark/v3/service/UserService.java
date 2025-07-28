package com.example.dtomappingbenchmark.v3.service;

import com.example.dtomappingbenchmark.v3.entity.User;
import com.example.dtomappingbenchmark.v3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
