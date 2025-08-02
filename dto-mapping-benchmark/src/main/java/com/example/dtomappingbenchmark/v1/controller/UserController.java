package com.example.dtomappingbenchmark.v1.controller;

import com.example.dtomappingbenchmark.v1.dto.UserDto;
import com.example.dtomappingbenchmark.v1.service.UserService;
import com.example.dtomappingbenchmark.v2.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto dto){
        long start = System.nanoTime();
        UserDto result = userService.create(dto);
        long end = System.nanoTime();
        log.info("v1 유저 생성 : {}ns", end - start);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        long start = System.nanoTime();
        UserDto result = userService.get(id);
        long end = System.nanoTime();
        log.info("v1 유저 조회 : {}ns", end - start);
        return ResponseEntity.ok(result);
    }
}
