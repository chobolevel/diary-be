package com.scrimmers.domain.entity.scrim.match

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.scrim.Scrim
import com.scrimmers.domain.entity.scrim.match.side.ScrimMatchSide
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "scrim_matches")
@Audited
class ScrimMatch(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var winnerSide: ScrimMatchWinnerSide
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scrim_id")
    var scrim: Scrim? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    @OneToMany(mappedBy = "scrimMatch", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Where(clause = "deleted = false")
    val scrimMatchSides = mutableListOf<ScrimMatchSide>()

    fun setBy(scrim: Scrim) {
        if (this.scrim != scrim) {
            this.scrim = scrim
        }
    }

    fun delete() {
        this.deleted = true
    }

    fun addScrimMatchSide(scrimMatchSide: ScrimMatchSide) {
        if (!this.scrimMatchSides.contains(scrimMatchSide)) {
            this.scrimMatchSides.add(scrimMatchSide)
        }
    }
}

enum class ScrimMatchWinnerSide {
    BLUE,
    RED
}

enum class ScrimMatchUpdateMask {
    WINNER_SIDE,
    BLUE_TEAM,
    RED_TEAM,
}
