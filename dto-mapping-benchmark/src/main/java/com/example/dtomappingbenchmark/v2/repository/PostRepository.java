package com.example.dtomappingbenchmark.v2.repository;

import com.example.dtomappingbenchmark.v2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("postRepositoryV2")
public interface PostRepository extends JpaRepository<Post, Long> {

}
