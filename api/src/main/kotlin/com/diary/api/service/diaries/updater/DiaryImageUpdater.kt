package com.diary.api.service.diaries.updater

import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.domain.entity.diaries.images.DiaryImage
import com.diary.domain.entity.diaries.images.DiaryImageUpdateMask
import org.springframework.stereotype.Component

@Component
class DiaryImageUpdater {

    fun markAsUpdate(
        request: UpdateDiaryImageRequestDto,
        entity: DiaryImage
    ): DiaryImage {
        request.updateMask.forEach {
            when (it) {
                DiaryImageUpdateMask.NAME -> entity.name = request.name!!
                DiaryImageUpdateMask.WIDTH -> entity.width = request.width!!
                DiaryImageUpdateMask.HEIGHT -> entity.height = request.height!!
                DiaryImageUpdateMask.URL -> entity.url = request.url!!
                DiaryImageUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
