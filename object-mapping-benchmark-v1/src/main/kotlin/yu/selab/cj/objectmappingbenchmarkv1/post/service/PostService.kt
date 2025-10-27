package yu.selab.cj.objectmappingbenchmarkv1.post.service

import org.springframework.stereotype.Service
import yu.selab.cj.objectmappingbenchmarkv1.post.command.PostDto
import yu.selab.cj.objectmappingbenchmarkv1.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv1.post.repository.PostRepository
import yu.selab.cj.objectmappingbenchmarkv1.user.repository.UserRepository

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {

    fun getPostList() = postRepository.findAll()

    fun getPost(id : Long) = postRepository.findById(id).orElseThrow {
        IllegalArgumentException("게시물 없음")
    }

    fun createPost(dto : PostDto) {
        val user = userRepository.findById(dto.userId).orElseThrow {
            IllegalArgumentException("유저 없음")
        }
        postRepository.save(Post(dto.title, dto.content, user))
    }

    fun deletePost(id : Long) {
        postRepository.deleteById(id)
    }

}