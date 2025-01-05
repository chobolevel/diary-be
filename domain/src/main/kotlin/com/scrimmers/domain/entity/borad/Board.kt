package com.scrimmers.domain.entity.borad

import com.scrimmers.domain.entity.BaseEntity
import com.scrimmers.domain.entity.borad.category.BoardCategory
import com.scrimmers.domain.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "boards")
@Audited
class Board(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var content: String
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_category_id")
    var boardCategory: BoardCategory? = null

    @Column(nullable = false)
    var deleted: Boolean = false

    fun setBy(user: User) {
        if (this.writer != user) {
            this.writer = user
        }
    }

    fun setBy(boardCategory: BoardCategory) {
        if (this.boardCategory != boardCategory) {
            this.boardCategory = boardCategory
        }
    }

    fun delete() {
        this.deleted = true
    }
}

enum class BoardOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class BoardUpdateMask {
    CATEGORY,
    TITLE,
    CONTENT
}
