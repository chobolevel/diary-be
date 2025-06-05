package com.diary.domain.entity.users.points

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

@Entity
@Table(name = "user_points")
class UserPoint(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false)
    val amount: Int,
    @Column(nullable = false, length = 255)
    var reason: String
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    fun set(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class UserPointOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
