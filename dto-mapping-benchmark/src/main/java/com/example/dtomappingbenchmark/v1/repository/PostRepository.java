package com.example.dtomappingbenchmark.v1.repository;

import com.example.dtomappingbenchmark.v1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("postRepositoryV1")
public interface PostRepository extends JpaRepository<Post, Long> {

}
