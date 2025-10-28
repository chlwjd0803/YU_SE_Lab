package yu.selab.cj.objectmappingbenchmarkv5.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import yu.selab.cj.objectmappingbenchmarkv5.user.command.CreateUserDto
import yu.selab.cj.objectmappingbenchmarkv5.user.command.ReadUserDto
import yu.selab.cj.objectmappingbenchmarkv5.user.entity.User

@Mapper(componentModel = "spring", uses = [PostMapper::class])
interface UserMapper {

    // CreateUserDto -> User 매핑 (posts는 기본값으로 빈 리스트)
    @Mapping(target = "posts", expression = "java(new java.util.ArrayList<Post>())")
    fun toEntity(dto: CreateUserDto): User

    // User -> ReadUserDto 매핑
    // user.uid -> readUserDto.userId
    // user.posts는 PostMapperV5를 통해 List<ReadPostDto>로 변환됨
    @Mapping(source = "uid", target = "userId")
    fun toDto(user: User): ReadUserDto

}