package com.daangn.domain.entity.likes

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.users.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited
import org.springframework.data.annotation.Immutable

@Entity
@Table(name = "likes")
@Audited
class Like(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: LikeType,
    @Column(nullable = false, length = 13)
    var targetId: String
): Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    fun set(user: User) {
        if(this.user != user) {
            this.user = user
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class LikeType {
    POST
}

enum class LikeOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
