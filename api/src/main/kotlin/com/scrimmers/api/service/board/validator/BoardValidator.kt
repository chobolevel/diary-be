package com.scrimmers.api.service.board.validator

import com.scrimmers.api.dto.board.CreateBoardRequestDto
import com.scrimmers.api.dto.board.UpdateBoardRequestDto
import com.scrimmers.domain.entity.borad.BoardUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class BoardValidator {

    fun validate(request: CreateBoardRequestDto) {
        if (request.title.length < 2) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "게시글 제목은 최소 2글자 이상이어야 합니다."
            )
        }
        if (request.content.length < 10) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "게시글 내용은 최소 10글자 이상이어야 합니다."
            )
        }
    }

    fun validate(request: UpdateBoardRequestDto) {
        request.updateMask.forEach {
            when (it) {
                BoardUpdateMask.CATEGORY -> {
                    if (request.boardCategoryId == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 카테고리가 유효하지 않습니다."
                        )
                    }
                }

                BoardUpdateMask.TITLE -> {
                    if (request.title.isNullOrEmpty() || request.title.length < 2) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 게시글 제목이 유효하지 않습니다.(최소 2글자 이상)"
                        )
                    }
                }

                BoardUpdateMask.CONTENT -> {
                    if (request.content.isNullOrEmpty() || request.content.length < 10) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 게시글 내용이 유효하지 않습니다.(최소 10글자 이상)"
                        )
                    }
                }
            }
        }
    }
}
