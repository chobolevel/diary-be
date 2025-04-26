package com.diary.domain.entity.users

import com.diary.domain.entity.common.BaseEntity
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
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    var id: String,
    @Column(nullable = false, updatable = false, unique = true, length = 30)
    var username: String,
    @Column(nullable = false, length = 255)
    var password: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var signUpType: UserSignUpType,
    @Column(nullable = false)
    var nickname: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var scope: UserScopeType
) : BaseEntity() {

    @Column(nullable = false)
    var resigned: Boolean = false
    fun resign() {
        this.resigned = true
    }
}

enum class UserSignUpType(var desc: String) {
    GENERAL("일반"),
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글")
}

enum class UserScopeType(val desc: String) {
    PUBLIC("전체 공개"),
    FRIENDS_ONLY("친구 공개"),
    PRIVATE("비공개")
}

enum class UserOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
