package com.scrimmers.domain.entity.user

import com.scrimmers.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "users")
@Audited
class User(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = true)
    var password: String? = null,
    @Column(nullable = true)
    var socialId: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var loginType: UserLoginType,
    @Column(nullable = false)
    var nickname: String,
    @Column(nullable = false)
    var phone: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRoleType
) : BaseEntity() {

    @Column(nullable = false)
    var resigned: Boolean = false

    fun resign() {
        this.resigned = true
    }
}

enum class UserLoginType {
    GENERAL,
    KAKAO,
    NAVER,
    GOOGLE;

    // equal to java static method
    companion object {
        fun find(value: String): UserLoginType? {
            return values().find { it.name == value }
        }
    }
}

enum class UserRoleType {
    ROLE_USER,
    ROLE_ADMIN
}

enum class UserOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}

enum class UserUpdateMask {
    NICKNAME,
    PHONE
}
