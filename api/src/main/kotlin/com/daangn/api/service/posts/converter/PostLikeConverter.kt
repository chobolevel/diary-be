package com.daangn.api.service.posts.converter

import com.daangn.api.dto.posts.likes.PostLikeResponseDto
import com.daangn.api.service.users.converter.UserConverter
import com.daangn.domain.entity.posts.likes.PostLike
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class PostLikeConverter(
    private val postConverter: PostConverter,
    private val userConverter: UserConverter,
) {

    fun convert(): PostLike {
        return PostLike(
            id = TSID.fast().toString()
        )
    }

    fun convert(entity: PostLike): PostLikeResponseDto {
        return PostLikeResponseDto(
            id = entity.id,
            post = postConverter.convert(entity.post!!),
            user = userConverter.convert(entity.user!!),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<PostLike>): List<PostLikeResponseDto> {
        return entities.map { convert(it) }
    }
}
