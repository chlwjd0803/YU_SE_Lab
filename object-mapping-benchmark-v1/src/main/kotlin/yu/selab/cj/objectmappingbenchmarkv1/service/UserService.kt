package yu.selab.cj.objectmappingbenchmarkv1.service

import org.springframework.stereotype.Service
import yu.selab.cj.objectmappingbenchmarkv1.command.UserDto
import yu.selab.cj.objectmappingbenchmarkv1.entity.User
import yu.selab.cj.objectmappingbenchmarkv1.entity.UserRepository

@Service
class UserService(
    private val userRepository : UserRepository
) {

    fun getUserList() = userRepository.findAll()

    fun getUser(id : Long) = userRepository.findById(id).orElseThrow {
        IllegalArgumentException("user not found")
    }

    fun createUser(dto : UserDto){
        val user = User(dto.name, dto.password)
        userRepository.save(user)
    }

    fun deleteUser(id : Long)
        = userRepository.deleteById(id)

}