package com.diary.domain.entity.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {

    fun findByIdAndResignedFalse(id: String): User?

    fun findByUsernameAndResignedFalse(username: String): User?

    fun existsByUsernameAndResignedFalse(username: String): Boolean
}
