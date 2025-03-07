package com.daangn.api.service.posts.converter

import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.service.categories.converter.CategoryConverter
import com.daangn.api.service.users.converter.UserConverter
import com.daangn.domain.entity.posts.Post
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class PostConverter(
    private val userConverter: UserConverter,
    private val categoryConverter: CategoryConverter,
) {

    fun convert(request: CreatePostRequestDto): Post {
        return Post(
            id = TSID.fast().toString(),
            title = request.title,
            content = request.content,
            salePrice = request.salePrice,
            freeShared = request.freeShared,
        )
    }

    fun convert(entity: Post): PostResponseDto {
        return PostResponseDto(
            id = entity.id,
            writer = userConverter.convert(entity.writer!!),
            category = categoryConverter.convert(entity.category!!),
            title = entity.title,
            content = entity.content,
            salePrice = entity.salePrice,
            isFreeShare = entity.freeShared,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Post>): List<PostResponseDto> {
        return entities.map { convert(it) }
    }
}
