package yu.selab.cj.objectmappingbenchmarkv1.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yu.selab.cj.objectmappingbenchmarkv1.command.UserDto
import yu.selab.cj.objectmappingbenchmarkv1.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService : UserService
) {

    @GetMapping
    fun getUserList()
        = ResponseEntity.ok().body(userService.getUserList())

    @GetMapping("/{id}")
    fun getUser(@PathVariable id : Long)
        = ResponseEntity.ok().body(userService.getUser(id))

    @PostMapping
    fun createUser(@RequestBody dto : UserDto)
        = ResponseEntity.ok().body(userService.createUser(dto))

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id : Long)
        = ResponseEntity.ok().body(userService.deleteUser(id))

}