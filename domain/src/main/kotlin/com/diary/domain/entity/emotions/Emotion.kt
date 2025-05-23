package com.diary.domain.entity.emotions

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "emotions")
@Audited
class Emotion(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false, length = 40)
    var icon: String,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class EmotionOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
