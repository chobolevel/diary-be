package com.daangn.api.service.posts.converter

import com.daangn.api.dto.posts.images.PostImageRequestDto
import com.daangn.api.dto.posts.images.PostImageResponseDto
import com.daangn.domain.entity.posts.image.PostImage
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class PostImageConverter {

    fun convert(request: PostImageRequestDto): PostImage {
        return PostImage(
            id = TSID.fast().toString(),
            type = request.type,
            url = request.url,
            name = request.name,
            order = request.order,
        )
    }

    fun convert(entity: PostImage): PostImageResponseDto {
        return PostImageResponseDto(
            id = entity.id,
            type = entity.type,
            url = entity.url,
            name = entity.name,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<PostImage>): List<PostImageResponseDto> {
        return entities.map { convert(it) }
    }
}
