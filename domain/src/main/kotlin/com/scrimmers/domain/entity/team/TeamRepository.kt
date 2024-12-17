package com.scrimmers.domain.entity.team

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, String> {

    fun findByIdAndDeletedFalse(id: String): Team?

    fun findByIdAndOwnerIdAndDeletedFalse(id: String, ownerId: String): Team?
}
