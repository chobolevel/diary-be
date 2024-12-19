package com.scrimmers.domain.entity.team.leave

import org.springframework.data.jpa.repository.JpaRepository

interface TeamLeaveRequestRepository : JpaRepository<TeamLeaveRequest, String> {

    fun findByIdAndDeletedFalse(id: String): TeamLeaveRequest?
}
