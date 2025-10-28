package yu.selab.cj.objectmappingbenchmarkv4.mapper

import yu.selab.cj.objectmappingbenchmarkv4.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv4.user.command.CreateUserDto
import yu.selab.cj.objectmappingbenchmarkv4.user.command.ReadUserDto
import yu.selab.cj.objectmappingbenchmarkv4.user.entity.User


object UserMapper {

    fun toEntity(dto : CreateUserDto) : User {
        return User(
            name = dto.name,
            password = dto.password,
            posts = mutableListOf()
        )
    }

    fun toDto(user : User) : ReadUserDto {
        return ReadUserDto(
            userId = user.uid,
            name = user.name,
//            posts = user.posts.map {
//                post -> ReadPostDto(
//                    pid = post.pid,
//                    title = post.title,
//                    content = post.content
//                )
//            }.toMutableList()
            posts = user.posts.asSequence()
                .map(PostMapper::toDto)
                .toMutableList()
        )
    }


}