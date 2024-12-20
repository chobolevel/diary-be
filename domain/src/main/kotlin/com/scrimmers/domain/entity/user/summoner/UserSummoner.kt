package com.scrimmers.domain.entity.user.summoner

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "user_summoners")
@Audited
class UserSummoner(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false, updatable = false)
    var summonerId: String,
    @Column(nullable = false, updatable = false)
    var summonerName: String,
    @Column(nullable = false, updatable = false)
    var summonerTag: String,
    @Column(nullable = false, updatable = false)
    var summonerLevel: Int,
    @Column(nullable = false, updatable = false)
    var summonerIconUrl: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var summonerSoloRank: SummonerRank,
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var summonerFlexRank: SummonerRank
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun setBy(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class SummonerRank {
    NONE,
    IRON,
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    EMERALD,
    DIAMOND,
    MASTER,
    GRAND_MASTER,
    CHALLENGER
}

enum class UserSummonerUpdateMask {
    SUMMONER_NAME,
    SUMMONER_TAG,
    SUMMONER_LEVEL,
    SUMMONER_ICON_URL,
    SUMMONER_SOLO_RANK,
    SUMMONER_FLEX_RANK
}
