package yu.selab.cj.objectmappingbenchmarkv1.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yu.selab.cj.objectmappingbenchmarkv1.post.entity.Post

@Repository
interface PostRepository : JpaRepository<Post, Long> {
}