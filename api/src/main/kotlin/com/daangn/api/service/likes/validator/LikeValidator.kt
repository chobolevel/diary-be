package com.daangn.api.service.likes.validator

import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.domain.entity.likes.LikeType
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class LikeValidator(
    private val postRepositoryWrapper: PostRepositoryWrapper
) {

    fun validate(request: LikeRequestDto) {
        val isExists = when (request.type) {
            LikeType.POST -> postRepositoryWrapper.existsById(request.targetId)
        }
        if (!isExists) {
            throw InvalidParameterException(
                errorCode = ErrorCode.LIKE_TARGET_NOT_FOUND,
                message = ErrorCode.LIKE_TARGET_NOT_FOUND.message
            )
        }
    }
}
