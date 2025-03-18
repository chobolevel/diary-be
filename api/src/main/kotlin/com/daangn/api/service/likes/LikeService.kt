package com.daangn.api.service.likes

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.service.likes.converter.LikeConverter
import com.daangn.api.service.likes.validator.LikeValidator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.likes.LikeOrderType
import com.daangn.domain.entity.likes.LikeQueryFilter
import com.daangn.domain.entity.likes.LikeRepositoryWrapper
import com.daangn.domain.entity.likes.LikeType
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LikeService(
    private val repositoryWrapper: LikeRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val postRepositoryWrapper: PostRepositoryWrapper,
    private val converter: LikeConverter,
    private val validator: LikeValidator
) {

    @Transactional
    fun likeOrUnlike(userId: String, request: LikeRequestDto): Boolean {
        validator.validate(request)
        val like = repositoryWrapper.findByUserIdAndTargetId(
            userId = userId,
            targetId = request.targetId
        )
        when (like == null) {
            true -> {
                val newLike = converter.convert(request).also {
                    val user = userRepositoryWrapper.findById(userId)
                    it.set(user)
                }
                repositoryWrapper.save(newLike)
                increaseLikeCount(
                    type = request.type,
                    targetId = request.targetId
                )
            }

            false -> {
                like.delete()
                decreaseLikeCount(
                    type = request.type,
                    targetId = request.targetId
                )
            }
        }
        return true
    }

    @Transactional(readOnly = true)
    fun getLikes(
        queryFilter: LikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<LikeOrderType>
    ): PaginationResponseDto {
        val likes = repositoryWrapper.search(
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
            data = converter.convert(likes),
            totalCount = totalCount
        )
    }

    private fun increaseLikeCount(type: LikeType, targetId: String) {
        when (type) {
            LikeType.POST -> {
                val post = postRepositoryWrapper.findById(targetId)
                post.increaseLikeCount()
            }
        }
    }

    private fun decreaseLikeCount(type: LikeType, targetId: String) {
        when (type) {
            LikeType.POST -> {
                val post = postRepositoryWrapper.findById(targetId)
                post.decreaseLikeCount()
            }
        }
    }
}
