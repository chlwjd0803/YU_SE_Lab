package yu.selab.cj.objectmappingbenchmarkv4.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yu.selab.cj.objectmappingbenchmarkv4.user.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
}