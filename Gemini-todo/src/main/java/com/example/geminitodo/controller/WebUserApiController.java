package com.example.geminitodo.controller;

import com.example.geminitodo.dto.WebUserDto;
import com.example.geminitodo.service.WebUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class WebUserApiController {
    private final WebUserService webUserService;

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody WebUserDto dto) {
        try {
            String token = webUserService.login(dto);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, webUserService.logout().toString()).body("로그아웃 성공");
    }
}
