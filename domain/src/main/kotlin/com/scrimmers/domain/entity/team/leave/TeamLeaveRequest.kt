package com.scrimmers.domain.entity.team.leave

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.team.Team
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
@Table(name = "team_leave_requests")
@Audited
class TeamLeaveRequest(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false)
    var comment: String,
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TeamLeaveRequestStatus = TeamLeaveRequestStatus.REQUESTED

    @Column(nullable = true)
    var rejectReason: String? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun setBy(team: Team) {
        if (this.team != team) {
            this.team = team
        }
    }

    fun setBy(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class TeamLeaveRequestStatus {
    REQUESTED,
    APPROVED,
    REJECTED,
}

enum class TeamLeaveRequestOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class TeamLeaveRequestUpdateMask {
    COMMENT
}
