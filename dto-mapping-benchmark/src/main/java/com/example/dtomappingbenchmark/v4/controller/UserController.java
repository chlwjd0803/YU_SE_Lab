package com.example.dtomappingbenchmark.v4.controller;

import com.example.dtomappingbenchmark.v4.dto.UserDto;
import com.example.dtomappingbenchmark.v4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto dto){
        long start = System.nanoTime();
        UserDto result = userService.create(dto);
        long end = System.nanoTime();
        log.info("v2 유저 생성 : {}ns", end - start);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        long start = System.nanoTime();
        UserDto result = userService.get(id);
        long end = System.nanoTime();
        log.info("v2 유저 조회 : {}ns", end - start);
        return ResponseEntity.ok(result);
    }
}
