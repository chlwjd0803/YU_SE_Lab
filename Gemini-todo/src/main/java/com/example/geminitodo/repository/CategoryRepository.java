package com.example.geminitodo.repository;

import com.example.geminitodo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndWebUserId(String name, Long userId);
    List<Category> findByWebUserId(Long userId);
}
