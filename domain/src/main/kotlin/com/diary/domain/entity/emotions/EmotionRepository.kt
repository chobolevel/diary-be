package com.diary.domain.entity.emotions

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface EmotionRepository : JpaRepository<Emotion, ID> {

    fun findByIdAndDeletedFalse(id: ID): Emotion?
}
