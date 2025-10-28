package yu.selab.cj.objectmappingbenchmarkv5.user.service


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yu.selab.cj.objectmappingbenchmarkv5.mapper.UserMapper
import yu.selab.cj.objectmappingbenchmarkv5.user.command.CreateUserDto
import yu.selab.cj.objectmappingbenchmarkv5.user.command.ReadUserDto
import yu.selab.cj.objectmappingbenchmarkv5.user.repository.UserRepository


@Service
class UserService(
    private val userRepository : UserRepository,
    private val userMapper : UserMapper,
) {

    @Transactional(readOnly = true)
    fun getUserList() = userRepository.findAll().map{
        user -> userMapper.toDto(user)
    }

    @Transactional(readOnly = true)
    fun getUser(id : Long) : ReadUserDto {

        val user = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("유저 없음")
        }

        return userMapper.toDto(user)
    }

    @Transactional
    fun createUser(dto : CreateUserDto){
        val user = userMapper.toEntity(dto)
        userRepository.save(user)
    }

    @Transactional
    fun deleteUser(id : Long)
        = userRepository.deleteById(id)

}