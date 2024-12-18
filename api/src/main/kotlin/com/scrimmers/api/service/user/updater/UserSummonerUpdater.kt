package com.scrimmers.api.service.user.updater

import com.scrimmers.api.dto.user.summoner.UpdateUserSummonerRequestDto
import com.scrimmers.domain.entity.user.summoner.UserSummoner
import com.scrimmers.domain.entity.user.summoner.UserSummonerUpdateMask
import org.springframework.stereotype.Component

@Component
class UserSummonerUpdater {

    fun markAsUpdate(request: UpdateUserSummonerRequestDto, entity: UserSummoner): UserSummoner {
        request.updateMask.forEach {
            when (it) {
                UserSummonerUpdateMask.SUMMONER_SOLO_RANK -> entity.summonerSoloRank = request.summonerSoloRank!!
                UserSummonerUpdateMask.SUMMONER_FLEX_RANK -> entity.summonerFlexRank = request.summonerFlexRank!!
            }
        }
        return entity
    }
}
