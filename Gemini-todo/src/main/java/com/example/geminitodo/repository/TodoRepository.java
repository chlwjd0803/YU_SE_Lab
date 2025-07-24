package com.example.geminitodo.repository;

import com.example.geminitodo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByWebUserIdAndStatus(Long userId, String status);
    List<Todo> findByWebUserIdAndDeadlineAndStatus(Long userId, LocalDate deadline, String status);
    List<Todo> findByWebUserIdAndFavoriteAndStatus(Long userId, boolean favorite, String status);
    List<Todo> findByCategoryId(Long categoryId);
    void deleteByWebUserId(Long userId);
}
