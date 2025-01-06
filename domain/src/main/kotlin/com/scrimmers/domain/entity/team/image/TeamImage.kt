package com.scrimmers.domain.entity.team.image

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
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "team_images")
@Audited
@Where(clause = "deleted = false")
class TeamImage(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: TeamImageType,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var url: String
) : BaseEntity() {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun setBy(team: Team) {
        if (this.team != team) {
            this.team = team
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class TeamImageType {
    LOGO
}

enum class TeamImageUpdateMask {
    TYPE,
    NAME,
    URL
}
