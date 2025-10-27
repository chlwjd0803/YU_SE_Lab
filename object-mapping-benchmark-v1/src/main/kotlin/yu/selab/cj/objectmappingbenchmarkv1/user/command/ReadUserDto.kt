package yu.selab.cj.objectmappingbenchmarkv1.user.command

import org.springframework.http.RequestEntity.post
import yu.selab.cj.objectmappingbenchmarkv1.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv1.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv1.user.entity.User

data class ReadUserDto(
    var userId : Long?,
    var name : String?,
    var posts : MutableList<ReadPostDto> = mutableListOf(),
){

    fun fromEntity(user : User) : ReadUserDto {

        this.userId = user.uid
        this.name = user.name

        this.posts = user.posts.map { post ->
            ReadPostDto(
                pid = post.pid,
                title = post.title,
                content = post.content,
            )
        }.toMutableList()

        return this
    }

}
