package com.diary.api.service.diaries.validator

import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.diaries.images.DiaryImageUpdateMask
import org.springframework.stereotype.Component

@Component
class DiaryImageValidator {

    fun validate(request: CreateDiaryImageRequestDto) {
        request.width.validateIsSmallerThan(
            compareTo = 0,
            parameterName = "width"
        )
        request.height.validateIsSmallerThan(
            compareTo = 0,
            parameterName = "height"
        )
        request.order.validateIsSmallerThan(
            compareTo = 1,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateDiaryImageRequestDto) {
        request.updateMask.forEach {
            when (it) {
                DiaryImageUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                DiaryImageUpdateMask.WIDTH -> {
                    request.width.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "width"
                    )
                }
                DiaryImageUpdateMask.HEIGHT -> {
                    request.height.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "height"
                    )
                }
                DiaryImageUpdateMask.URL -> {
                    request.url.validateIsNull(parameterName = "url")
                }
                DiaryImageUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
