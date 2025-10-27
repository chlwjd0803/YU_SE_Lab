package yu.selab.cj.objectmappingbenchmarkv4.user.command


import yu.selab.cj.objectmappingbenchmarkv4.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv4.user.entity.User
import kotlin.collections.map

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
