package com.diary.domain.entity.users.images

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.users.User
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "user_images")
@Audited
class UserImage(
    @Id
    @Column(nullable = false, updatable = false, unique = false, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false)
    var width: Int,
    @Column(nullable = false)
    var height: Int,
    @Column(nullable = false, length = 255)
    var url: String,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    fun set(user: User) {
        if (this.user != user) {
            this.user = user
        }
        if (!user.images.contains(this)) {
            user.images.add(this)
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class UserImageUpdateMask {
    NAME,
    WIDTH,
    HEIGHT,
    URL,
    ORDER
}
