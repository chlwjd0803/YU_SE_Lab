package yu.selab.cj.objectmappingbenchmarkv1.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User(
    @Column
    val name : String,

    @Column
    val password : String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uid : Long = 0
}