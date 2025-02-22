package com.daangn.domain.entity.users

import com.daangn.domain.entity.Audit
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
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Column(nullable = false, length = 40)
    var email: String,
    @Column(nullable = true, length = 100)
    var password: String?,
    @Column(nullable = true, length = 100)
    var socialId: String?,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var signUpType: UserSignUpType,
    @Column(nullable = false, length = 20)
    var nickname: String,
) : Audit() {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var role: UserRoleType = UserRoleType.ROLE_USER

    @Column(nullable = false)
    var resigned = false
    fun resign() {
        this.resigned = true
    }
}

enum class UserSignUpType {
    GENERAL,
    KAKAO,
}

enum class UserRoleType {
    ROLE_MASTER,
    ROLE_ADMIN,
    ROLE_USER
}
