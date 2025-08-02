package com.example.dtomappingbenchmark.v4.controller;

import com.example.dtomappingbenchmark.v4.dto.UserDto;
import com.example.dtomappingbenchmark.v4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto dto){
        UserDto result = userService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        UserDto result = userService.get(id);
        return ResponseEntity.ok(result);
    }
}
