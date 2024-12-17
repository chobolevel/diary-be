package com.scrimmers.domain.entity.team.image

import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class TeamImageFinder(
    private val repository: TeamImageRepository
) {

    fun findById(id: String): TeamImage {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.TEAM_IMAGE_IS_NOT_FOUND,
            message = ErrorCode.TEAM_IMAGE_IS_NOT_FOUND.desc
        )
    }
}
