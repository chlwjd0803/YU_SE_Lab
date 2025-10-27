package yu.selab.cj.objectmappingbenchmarkv4.user.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import yu.selab.cj.objectmappingbenchmarkv4.post.entity.Post


@Entity
class User(
    @Column
    val name : String,

    @Column
    val password : String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val posts : MutableList<Post> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uid : Long = 0
}