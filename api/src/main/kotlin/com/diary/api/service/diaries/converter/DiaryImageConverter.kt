package com.diary.api.service.diaries.converter

import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.domain.entity.diaries.images.DiaryImage
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class DiaryImageConverter {

    fun convert(request: CreateDiaryImageRequestDto): DiaryImage {
        return DiaryImage(
            id = TSID.fast().toString(),
            name = request.name,
            width = request.width,
            height = request.height,
            url = request.url,
            order = request.order
        )
    }
}
