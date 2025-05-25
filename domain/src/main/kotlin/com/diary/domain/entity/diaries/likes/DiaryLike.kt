package com.diary.domain.entity.diaries.likes

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.users.User
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "diary_likes")
@Audited
class DiaryLike(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    var id: ID,
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    var diary: Diary? = null
    fun set(diary: Diary) {
        if (this.diary != diary) {
            this.diary = diary
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    fun set(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class DiaryLikeOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
