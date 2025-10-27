package yu.selab.cj.objectmappingbenchmarkv1.post.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import yu.selab.cj.objectmappingbenchmarkv1.user.entity.User

@Entity
class Post(

    @Column
    val title : String,

    @Column
    val content : String?,

    @ManyToOne
    @JoinColumn(name = "uid")
    val user : User
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pid : Long = 0

}