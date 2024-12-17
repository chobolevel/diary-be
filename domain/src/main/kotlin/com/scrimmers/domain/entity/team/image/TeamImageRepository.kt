package com.scrimmers.domain.entity.team.image

import org.springframework.data.jpa.repository.JpaRepository

interface TeamImageRepository : JpaRepository<TeamImage, String> {

    fun findByIdAndDeletedFalse(id: String): TeamImage?
}
