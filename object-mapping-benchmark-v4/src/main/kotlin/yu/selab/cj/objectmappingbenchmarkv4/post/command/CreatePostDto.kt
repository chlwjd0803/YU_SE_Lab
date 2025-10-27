package yu.selab.cj.objectmappingbenchmarkv4.post.command

import yu.selab.cj.objectmappingbenchmarkv4.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv4.user.entity.User

data class CreatePostDto(
    val title : String,
    val content : String,
    val userId : Long
) {
    fun toEntity(user : User) = Post(title, content, user)
}
