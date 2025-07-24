package com.example.geminitodo.controller;

import com.example.geminitodo.dto.CategoryDto;
import com.example.geminitodo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos/index")
public class CategoryApiController {
    private final CategoryService categoryService;

    private Long getUserId(UserDetails userDetails) {
        // 실제 구현에서는 UserDetails에서 ID를 가져오는 로직 필요
        return 1L; // 임시 ID
    }

    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(categoryService.addCategory(getUserId(userDetails), dto));
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.editCategory(id, dto));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteAllCategories(@AuthenticationPrincipal UserDetails userDetails) {
        categoryService.deleteAllCategories(getUserId(userDetails));
        return ResponseEntity.ok().build();
    }
}
