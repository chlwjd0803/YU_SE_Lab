package com.example.geminitodo.service;

import com.example.geminitodo.domain.Category;
import com.example.geminitodo.domain.WebUser;
import com.example.geminitodo.dto.CategoryDto;
import com.example.geminitodo.repository.CategoryRepository;
import com.example.geminitodo.repository.TodoRepository;
import com.example.geminitodo.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final WebUserRepository webUserRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public Category addCategory(Long userId, CategoryDto dto) {
        WebUser user = webUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (categoryRepository.findByNameAndWebUserId(dto.getName(), userId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }
        Category category = dto.toEntity(user);
        return categoryRepository.save(category);
    }

    @Transactional
    public CategoryDto editCategory(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        // category.setName(dto.getName());
        return dto;
    }

    @Transactional
    public void deleteCategory(Long id) {
        todoRepository.findByCategoryId(id).forEach(todo -> todoRepository.deleteById(todo.getId()));
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCategories(Long userId) {
        List<Category> categories = categoryRepository.findByWebUserId(userId);
        categories.forEach(category -> {
            todoRepository.findByCategoryId(category.getId()).forEach(todo -> todoRepository.deleteById(todo.getId()));
            categoryRepository.deleteById(category.getId());
        });
    }
}
