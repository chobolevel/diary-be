package com.scrimmers.domain.entity.team.join

import org.springframework.data.jpa.repository.JpaRepository

interface TeamJoinRequestRepository : JpaRepository<TeamJoinRequest, String> {

    fun findByIdAndDeletedFalse(id: String): TeamJoinRequest?
}
