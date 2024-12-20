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
                UserSummonerUpdateMask.SUMMONER_NAME -> entity.summonerName = request.summonerName!!
                UserSummonerUpdateMask.SUMMONER_TAG -> entity.summonerTag = request.summonerTag!!
                UserSummonerUpdateMask.SUMMONER_LEVEL -> entity.summonerLevel = request.summonerLevel!!
                UserSummonerUpdateMask.SUMMONER_ICON_URL -> entity.summonerIconUrl = request.summonerIconUrl!!
                UserSummonerUpdateMask.SUMMONER_SOLO_RANK -> entity.summonerSoloRank = request.summonerSoloRank!!
                UserSummonerUpdateMask.SUMMONER_FLEX_RANK -> entity.summonerFlexRank = request.summonerFlexRank!!
            }
        }
        return entity
    }
}
