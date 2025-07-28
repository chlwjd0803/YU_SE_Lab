package com.example.dtomappingbenchmark.v3.repository;

import com.example.dtomappingbenchmark.v3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("postRepositoryV3")
public interface PostRepository extends JpaRepository<Post, Long> {
}
