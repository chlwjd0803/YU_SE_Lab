package com.example.geminitodo.repository;

import com.example.geminitodo.domain.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    Optional<WebUser> findByUsername(String username);
    Optional<WebUser> findByEmail(String email);
}
