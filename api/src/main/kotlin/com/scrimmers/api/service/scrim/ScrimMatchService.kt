package com.scrimmers.api.service.scrim

import com.scrimmers.api.dto.scrim.match.CreateScrimMatchRequestDto
import com.scrimmers.api.dto.scrim.match.UpdateScrimMatchRequestDto
import com.scrimmers.api.service.scrim.converter.ScrimMatchConverter
import com.scrimmers.api.service.scrim.converter.ScrimMatchSideConverter
import com.scrimmers.api.service.scrim.updater.ScrimMatchUpdater
import com.scrimmers.api.service.scrim.validator.ScrimMatchValidator
import com.scrimmers.domain.entity.scrim.Scrim
import com.scrimmers.domain.entity.scrim.ScrimFinder
import com.scrimmers.domain.entity.scrim.ScrimType
import com.scrimmers.domain.entity.scrim.match.ScrimMatchFinder
import com.scrimmers.domain.entity.scrim.match.ScrimMatchRepository
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScrimMatchService(
    private val repository: ScrimMatchRepository,
    private val finder: ScrimMatchFinder,
    private val scrimFinder: ScrimFinder,
    private val userFinder: UserFinder,
    private val converter: ScrimMatchConverter,
    private val scrimMatchSideConverter: ScrimMatchSideConverter,
    private val validator: ScrimMatchValidator,
    private val updater: ScrimMatchUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateScrimMatchRequestDto): String {
        val scrim = scrimFinder.findById(request.scrimId)
        validateScrimMatchesMaxCount(scrim)
        validateHomeTeamOrAwayTeamOwner(
            userId = userId,
            homeTeam = scrim.homeTeam!!,
            awayTeam = scrim.awayTeam!!,
        )
        val scrimMatch = converter.convert(request).also { scrimMatch ->
            scrimMatch.setBy(scrim)
            request.pogUserId?.let { pogUserId ->
                val homeTeamUserIds = userFinder.findByTeamId(scrim.homeTeam!!.id).map { it.id }
                val awayTeamUserIds = userFinder.findByTeamId(scrim.awayTeam!!.id).map { it.id }
                val scrimUserIds = homeTeamUserIds.union(awayTeamUserIds)
                if (pogUserId !in scrimUserIds) {
                    throw PolicyException(
                        errorCode = ErrorCode.USER_IS_NOT_FOUND,
                        message = "POG 플레이어를 찾을 수 없습니다.(양팀 소속 플레이어 중에서 설정할 수 있습니다.)"
                    )
                }
                scrimMatch.setBy(userFinder.findById(pogUserId))
            }
        }
        val savedScrimMatch = repository.save(scrimMatch)
        scrimMatchSideConverter.convertBlueTeam(request.blueTeam).also {
            it.setBy(savedScrimMatch)
        }
        scrimMatchSideConverter.convertRedTeam(request.redTeam).also {
            it.setBy(savedScrimMatch)
        }
        return savedScrimMatch.id
    }

    @Transactional
    fun update(userId: String, scrimMatchId: String, request: UpdateScrimMatchRequestDto): String {
        validator.validate(request)
        val scrimMatch = finder.findById(scrimMatchId)
        validateHomeTeamOrAwayTeamOwner(
            userId = userId,
            homeTeam = scrimMatch.scrim!!.homeTeam!!,
            awayTeam = scrimMatch.scrim!!.awayTeam!!,
        )
        updater.markAsUpdate(
            request = request,
            entity = scrimMatch
        )
        return scrimMatch.id
    }

    @Transactional
    fun delete(userId: String, scrimMatchId: String): Boolean {
        val scrimMatch = finder.findById(scrimMatchId)
        validateHomeTeamOrAwayTeamOwner(
            userId = userId,
            homeTeam = scrimMatch.scrim!!.homeTeam!!,
            awayTeam = scrimMatch.scrim!!.awayTeam!!,
        )
        scrimMatch.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateScrimMatchesMaxCount(scrim: Scrim) {
        when (scrim.type) {
            ScrimType.BO_1 -> {
                if (scrim.scrimMatches.size >= 1) {
                    throw PolicyException(
                        errorCode = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT,
                        message = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT.desc
                    )
                }
            }

            ScrimType.BO_3 -> {
                if (scrim.scrimMatches.size >= 3) {
                    throw PolicyException(
                        errorCode = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT,
                        message = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT.desc
                    )
                }
                scrim.scrimMatches.groupBy { it.winnerSide }.values.forEach {
                    if (it.size >= 2) {
                        throw PolicyException(
                            errorCode = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT,
                            message = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT.desc
                        )
                    }
                }
            }

            ScrimType.BO_5 -> {
                if (scrim.scrimMatches.size >= 5) {
                    throw PolicyException(
                        errorCode = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT,
                        message = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT.desc
                    )
                }
                scrim.scrimMatches.groupBy { it.winnerSide }.values.forEach {
                    if (it.size >= 3) {
                        throw PolicyException(
                            errorCode = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT,
                            message = ErrorCode.CAN_NOT_OVER_BEST_OF_COUNT.desc
                        )
                    }
                }
            }
        }
    }

    @Throws(PolicyException::class)
    private fun validateHomeTeamOrAwayTeamOwner(userId: String, homeTeam: Team, awayTeam: Team) {
        if (awayTeam.owner!!.id != userId && homeTeam.owner!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_OWNER.desc
            )
        }
    }
}
