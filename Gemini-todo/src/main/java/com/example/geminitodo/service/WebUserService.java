package com.example.geminitodo.service;

import com.example.geminitodo.domain.WebUser;
import com.example.geminitodo.dto.WebUserDto;
import com.example.geminitodo.jwt.JwtUtil;
import com.example.geminitodo.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebUserService {
    private final WebUserRepository webUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public WebUser signup(WebUserDto dto) {
        if (webUserRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        if (webUserRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        WebUser user = WebUser.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build();

        return webUserRepository.save(user);
    }

    public String login(WebUserDto dto) {
        WebUser user = webUserRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getId());
    }

    public ResponseCookie logout() {
        return ResponseCookie.from("jwtToken", "")
                .maxAge(0)
                .path("/")
                .build();
    }
}
