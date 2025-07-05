package com.example.todo_list.controller;

import com.example.todo_list.dto.StatusGroup;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.service.CategoryService;
import com.example.todo_list.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryService categoryService;

    // 시작화면 테스트중
    @GetMapping("/start")
    public String start(){
        return "/todos/start";
    }

    @GetMapping("/login")
    public String login(){
        return "/todos/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "/todos/signup";
    }

//    @GetMapping("/todos/statistic")
//    public String statistic(){
//        return "/todos/statistic";
//    }

    // 전체 목록 보기
    @GetMapping("/index")
    public String index(Model md){
        List<Category> categories = categoryService.getCategories();
        List<Todo> readyTodos = todoService.index("준비");
        List<Todo> completedTodos = todoService.index("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", readyTodos));
        statuses.add(new StatusGroup("완료", completedTodos));

        md.addAttribute("categories", categories);
        md.addAttribute("statuses", statuses);
        return "todos/index";
    }

    @GetMapping("/today")
    public String today(Model md){
        List<Category> categories = categoryService.getCategories();
        List<Todo> todayReadyTodos = todoService.today("준비");
        List<Todo> todayCompletedTodos = todoService.today("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", todayReadyTodos));
        statuses.add(new StatusGroup("완료", todayCompletedTodos));

        md.addAttribute("categories", categories);
        md.addAttribute("statuses", statuses);
        return "todos/today";
    }

    @GetMapping("/favorite")
    public String favorite(Model md){
        List<Category> categories = categoryService.getCategories();
        List<Todo> favReadyTodos = todoService.favorite("준비");
        List<Todo> favCompletedTodos = todoService.favorite("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", favReadyTodos));
        statuses.add(new StatusGroup("완료", favCompletedTodos));

        md.addAttribute("categories", categories);
        md.addAttribute("statuses", statuses);
        return "todos/favorite";
    }

    @GetMapping("/schedule")
    public String schedule(Model md){
        List<Todo> tomorrow = todoService.getTomorrowTodos();
        List<Todo> sevenDays = todoService.getSevenDaysTodos();
        List<Todo> fourteenDays = todoService.getFourteenDaysTodos();
        md.addAttribute("tomorrow", tomorrow);
        md.addAttribute("sevenDays", sevenDays);
        md.addAttribute("fourteenDays", fourteenDays);
        return "todos/schedule";
    }
}
