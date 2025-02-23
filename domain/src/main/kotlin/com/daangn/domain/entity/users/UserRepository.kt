package com.daangn.domain.entity.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByIdAndResignedFalse(id: String): User?

    fun findByEmailAndResignedFalse(email: String): User?

    fun existsByEmailAndResignedFalse(email: String): Boolean
}
