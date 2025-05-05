package com.diary.domain.entity.weathers

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "weathers")
@Audited
class Weather(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false, length = 40)
    var icon: String,
    @Column(nullable = false)
    var order: Int = 0,
) : BaseEntity() {

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class WeatherOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class WeatherUpdateMask {
    NAME,
    ICON,
    ORDER
}
