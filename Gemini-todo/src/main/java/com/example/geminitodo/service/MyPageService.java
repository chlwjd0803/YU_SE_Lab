package com.example.geminitodo.service;

import com.example.geminitodo.dto.MyPageDto;
import com.example.geminitodo.dto.UserRankDto;
import com.example.geminitodo.repository.TodoRepository;
import com.example.geminitodo.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final WebUserRepository webUserRepository;
    private final TodoRepository todoRepository;

    public MyPageDto getMyPageInfo(Long userId) {
        // Dummy data for now
        return new MyPageDto(0.0, 0, Collections.emptyList());
    }
}
