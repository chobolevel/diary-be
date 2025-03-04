package com.daangn.domain.entity.users.regions

import org.springframework.data.jpa.repository.JpaRepository

interface UserRegionRepository : JpaRepository<UserRegion, String> {

    fun findByIdAndDeletedFalse(id: String): UserRegion?
}
