package com.example.dtomappingbenchmark.v2.repository;

import com.example.dtomappingbenchmark.v2.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepositoryV2")
public interface UserRepository extends JpaRepository <User, Long>{
    @EntityGraph(attributePaths = "posts") // ← 여기가 핵심
    Optional<User> findById(Long id);
}
