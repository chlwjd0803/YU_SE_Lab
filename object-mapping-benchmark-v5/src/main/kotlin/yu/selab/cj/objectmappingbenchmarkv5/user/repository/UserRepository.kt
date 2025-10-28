package yu.selab.cj.objectmappingbenchmarkv5.user.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yu.selab.cj.objectmappingbenchmarkv5.user.entity.User
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
}