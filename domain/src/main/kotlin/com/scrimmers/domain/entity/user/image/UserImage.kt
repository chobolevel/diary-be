package com.scrimmers.domain.entity.user.image

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "user_images")
@Audited
@Where(clause = "deleted = false")
class UserImage(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: UserImageType,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var url: String
) : BaseEntity() {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun setBy(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class UserImageType {
    PROFILE
}

enum class UserImageUpdateMask {
    TYPE,
    NAME,
    URL
}
