package yu.selab.cj.objectmappingbenchmarkv1.post.service

import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.RequestEntity.post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yu.selab.cj.objectmappingbenchmarkv1.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv1.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv1.post.repository.PostRepository
import yu.selab.cj.objectmappingbenchmarkv1.user.repository.UserRepository

@Service
class PostService(
    private val postRepository : PostRepository,
    private val userRepository : UserRepository
) {

    @Transactional(readOnly = true)
    fun getPostList() = postRepository.findAll().map{
        post -> ReadPostDto(null, null, null).fromEntity(post)
    }

    @Transactional(readOnly = true)
    fun getPost(id : Long) : ReadPostDto {
        val post = postRepository.findById(id).orElseThrow {
            IllegalArgumentException("게시물 없음")
        }

        return ReadPostDto(null, null, null).fromEntity(post)
    }

    @Transactional
    fun createPost(dto : CreatePostDto) {
        val user = userRepository.findById(dto.userId).orElseThrow {
            IllegalArgumentException("유저 없음")
        }
        val post = dto.toEntity(user)
        postRepository.save(post)

//        postRepository.save(Post(dto.title, dto.content, user))
    }

    @Transactional
    fun deletePost(id : Long) {
        postRepository.deleteById(id)
    }

}