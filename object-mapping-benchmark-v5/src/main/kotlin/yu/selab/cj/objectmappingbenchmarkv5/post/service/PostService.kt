package yu.selab.cj.objectmappingbenchmarkv5.post.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yu.selab.cj.objectmappingbenchmarkv5.mapper.PostMapper
import yu.selab.cj.objectmappingbenchmarkv5.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv5.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv5.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv5.post.repository.PostRepository
import yu.selab.cj.objectmappingbenchmarkv5.user.repository.UserRepository
import kotlin.collections.map


@Service
class PostService(
    private val postRepository : PostRepository,
    private val userRepository : UserRepository,
    private val postMapper : PostMapper,
) {

    @Transactional(readOnly = true)
    fun getPostList() = postRepository.findAll().map{
        post -> postMapper.toDto(post)
    }

    @Transactional(readOnly = true)
    fun getPost(id : Long) : ReadPostDto {
        val post = postRepository.findById(id).orElseThrow {
            IllegalArgumentException("게시물 없음")
        }
        return postMapper.toDto(post)
    }

    @Transactional
    fun createPost(dto : CreatePostDto) {
        val user = userRepository.findById(dto.userId).orElseThrow {
            IllegalArgumentException("유저 없음")
        }
        val post = Post(dto.title, dto.content, user)
        postRepository.save(post)
    }

    @Transactional
    fun deletePost(id : Long) {
        postRepository.deleteById(id)
    }

}