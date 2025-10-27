package yu.selab.cj.objectmappingbenchmarkv4.user.service


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yu.selab.cj.objectmappingbenchmarkv4.mapper.UserMapper
import yu.selab.cj.objectmappingbenchmarkv4.user.command.CreateUserDto
import yu.selab.cj.objectmappingbenchmarkv4.user.command.ReadUserDto
import yu.selab.cj.objectmappingbenchmarkv4.user.repository.UserRepository


@Service
class UserService(
    private val userRepository : UserRepository
) {

    @Transactional(readOnly = true)
    fun getUserList() = userRepository.findAll().map{
//        user -> ReadUserDto(null, null).fromEntity(user)
        user -> UserMapper.toDto(user)
    }

    @Transactional(readOnly = true)
    fun getUser(id : Long) : ReadUserDto {
        val user = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("유저 없음")
        }

//        return ReadUserDto(null, null).fromEntity(user)
        return UserMapper.toDto(user)
    }

    @Transactional
    fun createUser(dto : CreateUserDto){
//        val user = dto.toEntity()
        val user = UserMapper.toEntity(dto)
        userRepository.save(user)
    }

    @Transactional
    fun deleteUser(id : Long)
        = userRepository.deleteById(id)

}