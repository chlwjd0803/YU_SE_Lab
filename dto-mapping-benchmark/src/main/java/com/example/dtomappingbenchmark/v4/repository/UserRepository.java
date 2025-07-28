package com.example.dtomappingbenchmark.v4.repository;

import com.example.dtomappingbenchmark.v4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryV4")
public interface UserRepository extends JpaRepository<User, Long> {
}
