package com.example.dtomappingbenchmark.v3.repository;

import com.example.dtomappingbenchmark.v3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryV3")
public interface UserRepository extends JpaRepository<User, Long> {
}
