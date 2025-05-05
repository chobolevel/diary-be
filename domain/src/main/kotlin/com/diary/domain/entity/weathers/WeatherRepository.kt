package com.diary.domain.entity.weathers

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface WeatherRepository : JpaRepository<Weather, ID> {

    fun findByIdAndDeletedFalse(id: ID): Weather?
}
