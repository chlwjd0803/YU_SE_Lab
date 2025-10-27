package yu.selab.cj.objectmappingbenchmarkv1.post.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yu.selab.cj.objectmappingbenchmarkv1.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv1.post.service.PostService

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {

    @GetMapping
    fun getPostList()
        = ResponseEntity.ok().body(postService.getPostList())

    @GetMapping("/{id}")
    fun getPost(@PathVariable id : Long)
        = ResponseEntity.ok().body(postService.getPost(id))

    @PostMapping
    fun createPost(@RequestBody dto : CreatePostDto)
        = ResponseEntity.ok().body(postService.createPost(dto))

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id : Long)
        = ResponseEntity.ok().body(postService.deletePost(id))

}