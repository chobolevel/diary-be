package com.scrimmers.domain.entity.team.scrim.match

import org.springframework.data.jpa.repository.JpaRepository

interface ScrimMatchRepository : JpaRepository<ScrimMatch, String> {

    fun findByIdAndDeletedFalse(id: String): ScrimMatch?
}
