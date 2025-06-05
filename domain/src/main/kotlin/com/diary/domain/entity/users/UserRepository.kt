package com.diary.domain.entity.users

import com.diary.domain.type.ID
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, String> {

    fun findByIdAndResignedFalse(id: String): User?

    // JPQL 사용시 해당 테이블명이 아닌 엔티티 클래스 이름을 매핑해야함
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.resigned = false")
    fun findByIdAndResignedFalseWithLock(id: ID): User?

    fun findByUsernameAndResignedFalse(username: String): User?

    fun existsByUsernameAndResignedFalse(username: String): Boolean
}
