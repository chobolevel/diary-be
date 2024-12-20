package com.scrimmers.domain.entity.scrim

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.scrim.match.ScrimMatch
import com.scrimmers.domain.entity.scrim.request.ScrimRequest
import com.scrimmers.domain.entity.team.Team
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OrderBy
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited
import java.time.LocalDateTime

@Entity
@Table(name = "scrims")
@Audited
class Scrim(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ScrimType,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var startedAt: LocalDateTime
) : BaseEntity() {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scrim_request_id")
    var scrimRequest: ScrimRequest? = null

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    var homeTeam: Team? = null

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    var awayTeam: Team? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    @OneToMany(mappedBy = "scrim", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Where(clause = "deleted = false")
    @OrderBy(clause = "order asc")
    val scrimMatches = mutableListOf<ScrimMatch>()

    fun setBy(scrimRequest: ScrimRequest) {
        if (this.scrimRequest != scrimRequest) {
            this.scrimRequest = scrimRequest
        }
    }

    fun initHomeTeam(team: Team) {
        if (this.homeTeam != team) {
            this.homeTeam = team
        }
    }

    fun initAwayTeam(team: Team) {
        if (this.awayTeam != team) {
            this.awayTeam = team
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class ScrimType(val ko: String) {
    BO_1("단판제"),
    BO_3("3판 2선제"),
    BO_5("5판 3선제")
}

enum class ScrimOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class ScrimUpdateMask {
    TYPE,
    NAME,
    STARTED_AT
}
