package com.scrimmers.domain.entity.user.summoner

import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class UserSummonerFinder(
    private val repository: UserSummonerRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): UserSummoner {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_SUMMONER_IS_NOT_FOUND,
            message = ErrorCode.USER_SUMMONER_IS_NOT_FOUND.desc
        )
    }
}
