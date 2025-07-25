package com.example.dtomappingbenchmark.v1.repository;

import com.example.dtomappingbenchmark.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryV1")
public interface UserRepository extends JpaRepository <User, Long>{
}
