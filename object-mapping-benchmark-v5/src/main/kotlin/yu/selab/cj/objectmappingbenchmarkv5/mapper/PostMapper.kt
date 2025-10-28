package yu.selab.cj.objectmappingbenchmarkv5.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import yu.selab.cj.objectmappingbenchmarkv5.post.command.CreatePostDto
import yu.selab.cj.objectmappingbenchmarkv5.post.command.ReadPostDto
import yu.selab.cj.objectmappingbenchmarkv5.post.entity.Post


@Mapper(componentModel = "spring") // 스프링 Bean으로 등록
interface PostMapper {
    // Post -> ReadPostDto 매핑
    @Mapping(source = "pid", target = "pid") // 필드명이 같아도 명시적으로 적어주는 것이 좋음
    fun toDto(post: Post): ReadPostDto
}