package yu.selab.cj.objectmappingbenchmarkv4.post.service

import org.springframework.http.RequestEntity.post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yu.selab.cj.objectmappingbenchmarkv4.mapper.PostMapper
import yu.selab.cj.objectmappingbenchmarkv4.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv4.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv4.post.repository.PostRepository
import yu.selab.cj.objectmappingbenchmarkv4.user.repository.UserRepository
import kotlin.collections.map


@Service
class PostService(
    private val postRepository : PostRepository,
    private val userRepository : UserRepository
) {

    @Transactional(readOnly = true)
    fun getPostList() = postRepository.findAll().map{
//        post -> ReadPostDto(null, null, null).fromEntity(post)
        post -> PostMapper.toDto(post)
    }

    @Transactional(readOnly = true)
    fun getPost(id : Long) : ReadPostDto {
        val post = postRepository.findById(id).orElseThrow {
            IllegalArgumentException("게시물 없음")
        }

        return PostMapper.toDto(post)
    }

    @Transactional
    fun createPost(dto : CreatePostDto) {
        val user = userRepository.findById(dto.userId).orElseThrow {
            IllegalArgumentException("유저 없음")
        }
        val post = PostMapper.toEntity(dto, user)
        postRepository.save(post)

//        postRepository.save(Post(dto.title, dto.content, user))
    }

    @Transactional
    fun deletePost(id : Long) {
        postRepository.deleteById(id)
    }

}