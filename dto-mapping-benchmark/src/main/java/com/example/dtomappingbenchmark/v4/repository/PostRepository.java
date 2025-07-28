package com.example.dtomappingbenchmark.v4.repository;

import com.example.dtomappingbenchmark.v4.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("postRepositoryV4")
public interface PostRepository extends JpaRepository<Post, Long> {
}
