package com.scrimmers.domain.entity.scrim.request

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.team.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "scrim_requests")
@Audited
class ScrimRequest(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false)
    var comment: String
) : BaseEntity() {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_team_id")
    var fromTeam: Team? = null

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "to_team_id")
    var toTeam: Team? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ScrimRequestStatus = ScrimRequestStatus.REQUESTED

    @Column(nullable = true)
    var rejectReason: String? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun initFromTeam(team: Team) {
        if (this.fromTeam != team) {
            this.fromTeam = team
        }
    }

    fun initToTeam(team: Team) {
        if (this.toTeam != team) {
            this.toTeam = team
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class ScrimRequestStatus {
    REQUESTED,
    APPROVED,
    REJECTED
}

enum class ScrimRequestOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class ScrimRequestUpdateMask {
    COMMENT
}
