package com.example.geminitodo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {

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
    public String mypage() {
        return "todos/mypage";
    }
}
