package yu.selab.cj.objectmappingbenchmarkv1.user.service

import org.springframework.stereotype.Service
import yu.selab.cj.objectmappingbenchmarkv1.user.command.UserDto
import yu.selab.cj.objectmappingbenchmarkv1.user.entity.User
import yu.selab.cj.objectmappingbenchmarkv1.user.repository.UserRepository

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