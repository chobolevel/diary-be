package com.daangn.api.service.posts.converter

import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.service.categories.converter.CategoryConverter
import com.daangn.api.service.users.converter.UserConverter
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.image.PostImageType
import com.daangn.domain.entity.posts.likes.PostLikeRepositoryWrapper
import io.hypersistence.tsid.TSID
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class PostConverter(
    private val userConverter: UserConverter,
    private val categoryConverter: CategoryConverter,
    private val postImageConverter: PostImageConverter,
    private val postLikeRepositoryWrapper: PostLikeRepositoryWrapper,
) {

    fun convert(request: CreatePostRequestDto): Post {
        val salePrice = when (request.freeShared) {
            true -> 0
            false -> request.salePrice
        }
        return Post(
            id = TSID.fast().toString(),
            title = request.title,
            content = request.content,
            salePrice = salePrice,
            freeShared = request.freeShared,
        )
    }

    fun convert(entity: Post): PostResponseDto {
        val userId: String? = SecurityContextHolder.getContext().authentication?.getUserId()
        val isLiked: Boolean = when (userId) {
            null -> false
            else -> postLikeRepositoryWrapper.findByPostIdAndUserId(
                postId = entity.id,
                userId = userId
            ) != null
        }
        return PostResponseDto(
            id = entity.id,
            writer = userConverter.convert(entity.writer!!),
            category = categoryConverter.convert(entity.category!!),
            status = entity.status,
            title = entity.title,
            content = entity.content,
            salePrice = entity.salePrice,
            isFreeShare = entity.freeShared,
            likeCount = entity.likeCount,
            isLiked = isLiked,
            mainImages = postImageConverter.convert(entity.getTypeImages(type = PostImageType.MAIN)),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Post>): List<PostResponseDto> {
        return entities.map { convert(it) }
    }
}
