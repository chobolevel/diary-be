package com.scrimmers.domain.entity.team

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.team.image.TeamImage
import com.scrimmers.domain.entity.user.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "teams")
@Audited
class Team(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var description: String
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: User? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    @OneToOne(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Where(clause = "deleted = false")
    var teamImage: TeamImage? = null

    fun setBy(owner: User) {
        if (this.owner != owner) {
            this.owner = owner
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class TeamOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class TeamUpdateMask {
    NAME,
    DESCRIPTION
}
