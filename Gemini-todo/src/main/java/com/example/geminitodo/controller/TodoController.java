package com.example.geminitodo.controller;

import com.example.geminitodo.dto.MyPageDto;
import com.example.geminitodo.service.MyPageService;
import com.example.geminitodo.service.WebUserService;
import com.example.geminitodo.dto.WebUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final MyPageService myPageService;
    private final WebUserService webUserService;

    @GetMapping("/start")
    public String start() {
        return "todos/start";
    }

    @GetMapping("/login")
    public String login() {
        return "todos/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "todos/signup";
    }

    @PostMapping("/signup") // 회원가입 폼 제출 처리
    public String signup(@ModelAttribute WebUserDto dto) {
        try {
            webUserService.signup(dto);
            return "redirect:/todos/login"; // 성공 시 로그인 페이지로 리다이렉션
        } catch (IllegalArgumentException e) {
            // 에러 발생 시 회원가입 페이지로 다시 리다이렉션 (에러 메시지 포함 가능)
            return "redirect:/todos/signup?error=" + e.getMessage();
        }
    }

    @GetMapping("/index")
    public String index() {
        return "todos/index";
    }

    @GetMapping("/today")
    public String today() {
        return "todos/today";
    }

    @GetMapping("/favorite")
    public String favorite() {
        return "todos/favorite";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "todos/schedule";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyPageDto myPageInfo = myPageService.getMyPageInfo(userId);
        model.addAttribute("myPageInfo", myPageInfo);
        return "todos/mypage";
    }
}
