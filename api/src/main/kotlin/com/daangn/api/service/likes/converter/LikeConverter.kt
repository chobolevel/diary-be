package com.daangn.api.service.likes.converter

import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.dto.likes.LikeResponseDto
import com.daangn.domain.entity.likes.Like
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class LikeConverter {

    fun convert(request: LikeRequestDto): Like {
        return Like(
            id = TSID.fast().toString(),
            type = request.type,
            targetId = request.targetId
        )
    }

    fun convert(entity: Like): LikeResponseDto {
        return LikeResponseDto(
            id = entity.id,
            type = entity.type,
            targetId = entity.targetId,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<Like>): List<LikeResponseDto> {
        return entities.map { convert(it) }
    }
}
