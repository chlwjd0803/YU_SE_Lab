package yu.selab.cj.objectmappingbenchmarkv1.post.command

import yu.selab.cj.objectmappingbenchmarkv1.post.entity.Post
import yu.selab.cj.objectmappingbenchmarkv1.user.entity.User

data class CreatePostDto(
    val title : String,
    val content : String,
    val userId : Long
) {
    fun toEntity(user : User) = Post(title, content, user)
}
