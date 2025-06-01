package com.diary.domain.entity.diaries.images

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.diaries.Diary
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
@Table(name = "diary_images")
@Audited
class DiaryImage(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false)
    var width: Int,
    @Column(nullable = false)
    var height: Int,
    @Column(nullable = false, length = 255)
    var url: String,
    @Column(nullable = false)
    var order: Int,
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    var diary: Diary? = null
    fun set(diary: Diary) {
        if (this.diary != diary) {
            this.diary = diary
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class DiaryImageUpdateMask {
    NAME,
    WIDTH,
    HEIGHT,
    URL,
    ORDER
}
