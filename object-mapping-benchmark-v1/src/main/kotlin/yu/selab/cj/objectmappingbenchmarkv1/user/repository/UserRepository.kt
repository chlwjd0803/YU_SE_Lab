package yu.selab.cj.objectmappingbenchmarkv1.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yu.selab.cj.objectmappingbenchmarkv1.user.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
}