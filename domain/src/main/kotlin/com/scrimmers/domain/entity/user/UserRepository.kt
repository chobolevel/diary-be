package com.scrimmers.domain.entity.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun findByIdAndResignedFalse(id: String): User?

    fun findByEmailAndLoginTypeAndResignedFalse(email: String, loginType: UserLoginType): User?

    fun findBySocialIdAndLoginTypeAndResignedFalse(socialId: String, loginType: UserLoginType): User?

    fun findByTeamIdAndResignedFalse(teamId: String): List<User>

    fun existsByTeamIdAndResignedFalse(teamId: String): Boolean

    fun findByIdInAndTeamIdAndResignedFalse(ids: List<String>, teamId: String): List<User>
}
