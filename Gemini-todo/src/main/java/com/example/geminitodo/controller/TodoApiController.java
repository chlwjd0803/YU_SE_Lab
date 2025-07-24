package com.example.geminitodo.controller;

import com.example.geminitodo.dto.TodoDto;
import com.example.geminitodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoApiController {
    private final TodoService todoService;

    private Long getUserId(UserDetails userDetails) {
        // 실제 구현에서는 UserDetails에서 ID를 가져오는 로직 필요
        return 1L; // 임시 ID
    }

    @GetMapping("/index")
    public ResponseEntity<?> getTasks(@RequestParam(required = false, defaultValue = "준비") String status, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(todoService.getTasks(getUserId(userDetails), status));
    }

    @PostMapping("/index/task")
    public ResponseEntity<?> addTask(@RequestBody TodoDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(todoService.addTask(getUserId(userDetails), dto));
    }

    @PostMapping("/today/task")
    public ResponseEntity<?> addTodayTask(@RequestBody TodoDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(todoService.addTask(getUserId(userDetails), dto));
    }

    @PostMapping("/favorite/task")
    public ResponseEntity<?> addFavoriteTask(@RequestBody TodoDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        dto.setFavorite(true);
        return ResponseEntity.ok(todoService.addTask(getUserId(userDetails), dto));
    }

    @PostMapping("/index/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(todoService.updateStatus(id, status));
    }

    @PatchMapping("/updateFavorite/{id}")
    public ResponseEntity<?> updateFavorite(@PathVariable Long id) {
        // todoService.updateFavorite(id) 구현 필요
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/index/task/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.editTask(id, dto));
    }

    @DeleteMapping("/index/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        todoService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
