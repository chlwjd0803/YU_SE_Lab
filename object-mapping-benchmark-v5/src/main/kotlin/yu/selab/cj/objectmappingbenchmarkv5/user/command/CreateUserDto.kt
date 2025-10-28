package yu.selab.cj.objectmappingbenchmarkv5.user.command

import yu.selab.cj.objectmappingbenchmarkv5.user.entity.User

data class CreateUserDto(
    var name : String,
    var password : String,
){

    fun toEntity() : User {
        return User(name, password)
    }

}