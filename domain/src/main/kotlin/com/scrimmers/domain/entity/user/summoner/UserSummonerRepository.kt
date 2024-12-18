package com.scrimmers.domain.entity.user.summoner

import org.springframework.data.jpa.repository.JpaRepository

interface UserSummonerRepository : JpaRepository<UserSummoner, String> {

    fun findByIdAndDeletedFalse(id: String): UserSummoner?
}
