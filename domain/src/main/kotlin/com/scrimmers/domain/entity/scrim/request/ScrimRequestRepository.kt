package com.scrimmers.domain.entity.scrim.request

import org.springframework.data.jpa.repository.JpaRepository

interface ScrimRequestRepository : JpaRepository<ScrimRequest, String> {

    fun findByIdAndDeletedFalse(id: String): ScrimRequest?
}
