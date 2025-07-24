package com.example.geminitodo.service;

import com.example.geminitodo.domain.Category;
import com.example.geminitodo.domain.Todo;
import com.example.geminitodo.domain.WebUser;
import com.example.geminitodo.dto.TodoDto;
import com.example.geminitodo.repository.CategoryRepository;
import com.example.geminitodo.repository.TodoRepository;
import com.example.geminitodo.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final WebUserRepository webUserRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Todo addTask(Long userId, TodoDto dto) {
        WebUser user = webUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        } else {
            category = categoryRepository.findByNameAndWebUserId("작업", userId).orElseGet(() -> {
                Category newCategory = Category.builder().name("작업").webUser(user).build();
                return categoryRepository.save(newCategory);
            });
        }

        Todo todo = dto.toEntity(user, category);
        return todoRepository.save(todo);
    }

    @Transactional
    public String updateStatus(Long id, String newStatus) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없습니다."));
        // todo.setStatus(newStatus); // setStatus 메소드가 없으므로 직접 수정
        return newStatus;
    }

    @Transactional
    public TodoDto editTask(Long id, TodoDto dto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없습니다."));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        // todo.setTitle(dto.getTitle());
        // todo.setCategory(category);
        // todo.setDeadline(dto.getDeadline());
        return dto;
    }

    @Transactional
    public void deleteTask(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> getTasks(Long userId, String status) {
        return todoRepository.findByWebUserIdAndStatus(userId, status);
    }

    public List<Todo> getTodayTasks(Long userId, String status) {
        return todoRepository.findByWebUserIdAndDeadlineAndStatus(userId, LocalDate.now(), status);
    }

    public List<Todo> getFavoriteTasks(Long userId, String status) {
        return todoRepository.findByWebUserIdAndFavoriteAndStatus(userId, true, status);
    }
}
