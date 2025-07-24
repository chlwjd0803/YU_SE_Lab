package com.example.geminitodo.controller;

import com.example.geminitodo.dto.MyPageDto;
import com.example.geminitodo.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final MyPageService myPageService;

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
