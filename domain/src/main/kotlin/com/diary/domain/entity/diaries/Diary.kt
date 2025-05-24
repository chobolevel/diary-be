package com.diary.domain.entity.diaries

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.users.User
import com.diary.domain.entity.weathers.Weather
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
@Table(name = "diaries")
@Audited
class Diary(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 80)
    var title: String,
    @Column(nullable = false)
    var content: String,
    @Column(nullable = false)
    var isSecret: Boolean
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User? = null
    fun set(writer: User) {
        if (this.writer != writer) {
            this.writer = writer
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id")
    var weather: Weather? = null
    fun set(weather: Weather) {
        if (this.weather != weather) {
            this.weather = weather
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id")
    var emotion: Emotion? = null
    fun set(emotion: Emotion) {
        if (this.emotion != emotion) {
            this.emotion = emotion
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class DiaryOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}

enum class DiaryUpdateMask {
    WEATHER,
    EMOTION,
    TITLE,
    CONTENT,
    IS_SECRET
}
