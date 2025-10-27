package yu.selab.cj.objectmappingbenchmarkv4.mapper

import yu.selab.cj.objectmappingbenchmarkv4.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv4.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv4.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv4.user.entity.User


object PostMapper {

    // 새로운 게시물 쓰기
    fun toEntity(dto : CreatePostDto, user : User) : Post {
        return Post(
            title = dto.title,
            content = dto.content,
            user = user
        )
    }

    fun toDto(post : Post) : ReadPostDto {
        return ReadPostDto(
            post.pid,
            post.title,
            post.content
        )
    }

}