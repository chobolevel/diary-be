package com.diary.api.service.users.validator

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.users.images.UserImageUpdateMask
import org.springframework.stereotype.Component

@Component
class UserImageValidator {

    fun validate(request: CreateUserImageRequestDto) {
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

    fun validate(request: UpdateUserImageRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserImageUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                UserImageUpdateMask.WIDTH -> {
                    request.width.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "width"
                    )
                }
                UserImageUpdateMask.HEIGHT -> {
                    request.height.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "height"
                    )
                }
                UserImageUpdateMask.URL -> {
                    request.url.validateIsNull(parameterName = "url")
                }
                UserImageUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
