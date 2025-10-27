package yu.selab.cj.objectmappingbenchmarkv1.post.command

import yu.selab.cj.objectmappingbenchmarkv1.post.entity.Post

data class ReadPostDto(
    var pid : Long?,
    var title : String?,
    var content : String?
){
    fun fromEntity(post : Post) : ReadPostDto {
        this.pid = post.pid
        this.title = post.title
        this.content = post.content
        return this
    }
}