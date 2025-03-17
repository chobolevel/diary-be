package com.daangn.api.service.posts

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.posts.converter.PostLikeConverter
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.entity.posts.likes.PostLikeOrderType
import com.daangn.domain.entity.posts.likes.PostLikeQueryFilter
import com.daangn.domain.entity.posts.likes.PostLikeRepositoryWrapper
import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostLikeService(
    private val repositoryWrapper: PostLikeRepositoryWrapper,
    private val converter: PostLikeConverter,
    private val postRepositoryWrapper: PostRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
) {

    @Transactional
    fun likeOrUnlike(userId: String, postId: String): Boolean {
        val postLike = repositoryWrapper.findByPostIdAndUserId(
            postId = postId,
            userId = userId
        )
        when (postLike == null) {
            true -> {
                val postLikeRequest = converter.convert().also {
                    val post = postRepositoryWrapper.findById(postId)
                    val user = userRepositoryWrapper.findById(userId)
                    it.set(post)
                    it.set(user)
                }
                repositoryWrapper.save(postLikeRequest)
            }
            else -> {
                postLike.delete()
            }
        }
        return true
    }

    @Transactional(readOnly = true)
    fun getPostLikes(
        queryFilter: PostLikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<PostLikeOrderType>
    ): PaginationResponseDto {
        val postLikes = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(postLikes),
            totalCount = totalCount
        )
    }
}
