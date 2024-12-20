package com.scrimmers.domain.entity.team.scrim.match

import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ScrimMatchFinder(
    private val repository: ScrimMatchRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): ScrimMatch {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.SCRIM_MATCH_IS_NOT_FOUND,
            message = ErrorCode.SCRIM_MATCH_IS_NOT_FOUND.desc
        )
    }
}
