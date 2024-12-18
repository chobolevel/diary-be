package com.scrimmers.api.service.user

import com.scrimmers.api.dto.user.summoner.CreateUserSummonerRequestDto
import com.scrimmers.api.dto.user.summoner.UpdateUserSummonerRequestDto
import com.scrimmers.api.service.user.converter.UserSummonerConverter
import com.scrimmers.api.service.user.updater.UserSummonerUpdater
import com.scrimmers.api.service.user.validator.UserSummonerValidator
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.entity.user.summoner.UserSummoner
import com.scrimmers.domain.entity.user.summoner.UserSummonerFinder
import com.scrimmers.domain.entity.user.summoner.UserSummonerRepository
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class UserSummonerService(
    private val repository: UserSummonerRepository,
    private val finder: UserSummonerFinder,
    private val userFinder: UserFinder,
    private val converter: UserSummonerConverter,
    private val validator: UserSummonerValidator,
    private val updater: UserSummonerUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateUserSummonerRequestDto): String {
        val user = userFinder.findById(userId)
        val isExists = finder.existsBySummonerId(request.summonerId)
        if (isExists) {
            throw PolicyException(
                errorCode = ErrorCode.USER_SUMMONER_IS_ALREADY_EXISTS,
                message = ErrorCode.USER_SUMMONER_IS_ALREADY_EXISTS.desc
            )
        }
        val userSummoner = converter.convert(request).also {
            it.setBy(user)
        }
        return repository.save(userSummoner).id
    }

    @Transactional
    fun update(userId: String, userSummonerId: String, request: UpdateUserSummonerRequestDto): String {
        validator.validate(request)
        val userSummoner = finder.findById(userSummonerId)
        validateUser(
            userId = userId,
            userSummoner = userSummoner
        )
        updater.markAsUpdate(
            request = request,
            entity = userSummoner
        )
        return userSummoner.id
    }

    @Transactional
    fun delete(userId: String, userSummonerId: String): Boolean {
        val userSummoner = finder.findById(userSummonerId)
        validateUser(
            userId = userId,
            userSummoner = userSummoner
        )
        userSummoner.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateUser(userId: String, userSummoner: UserSummoner) {
        if (userSummoner.user!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.ONLY_ACCESS_FOR_USER_SUMMONER_OWNER,
                message = ErrorCode.ONLY_ACCESS_FOR_USER_SUMMONER_OWNER.desc
            )
        }
    }
}
