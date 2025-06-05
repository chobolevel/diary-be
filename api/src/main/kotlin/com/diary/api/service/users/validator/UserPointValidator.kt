package com.diary.api.service.users.validator

import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.util.validateIsSmallerThan
import org.springframework.stereotype.Component

@Component
class UserPointValidator {

    fun validate(request: AddUserPointRequestDto) {
        request.amount.validateIsSmallerThan(
            compareTo = 100,
            parameterName = "amount"
        )
    }

    fun validate(request: SubUserPointRequestDto) {
        request.amount.validateIsSmallerThan(
            compareTo = 100,
            parameterName = "amount"
        )
    }
}
