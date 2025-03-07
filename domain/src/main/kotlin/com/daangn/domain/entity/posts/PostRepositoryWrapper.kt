package com.daangn.domain.entity.posts

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.QPost.post
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class PostRepositoryWrapper(
    private val repository: PostRepository,
    private val customRepository: PostCustomRepository
) {

    fun save(post: Post): Post {
        return repository.save(post)
    }

    fun search(
        queryFilter: PostQueryFilter,
        pagination: Pagination,
        orderTypes: List<PostOrderType>
    ): List<Post> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: PostQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Post {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.POST_NOT_FOUND,
            message = ErrorCode.POST_NOT_FOUND.message
        )
    }

    private fun List<PostOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                PostOrderType.CREATED_AT_ASC -> post.createdAt.asc()
                PostOrderType.CREATED_AT_DESC -> post.createdAt.desc()
            }
        }.toTypedArray()
    }
}
