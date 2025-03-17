package com.daangn.domain.entity.posts.likes

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.likes.QPostLike.postLike
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class PostLIkeRepositoryWrapper(
    private val repository: PostLikeRepository,
    private val customRepository: PostLikeCustomRepository
) {

    fun save(postLike: PostLike): PostLike {
        return repository.save(postLike)
    }

    fun search(
        queryFilter: PostLikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<PostLikeOrderType>
    ): List<PostLike> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: PostLikeQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): PostLike {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.POST_LIKE_NOT_FOUND,
            message = ErrorCode.POST_LIKE_NOT_FOUND.message
        )
    }

    private fun List<PostLikeOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                PostLikeOrderType.CREATED_AT_ASC -> postLike.createdAt.asc()
                PostLikeOrderType.CREATED_AT_DESC -> postLike.createdAt.desc()
            }
        }.toTypedArray()
    }
}
