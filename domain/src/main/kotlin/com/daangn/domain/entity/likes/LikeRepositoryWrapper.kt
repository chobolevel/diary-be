package com.daangn.domain.entity.likes

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.likes.QLike.like
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class LikeRepositoryWrapper(
    private val repository: LikeRepository,
    private val customRepository: LikeCustomRepository
) {

    fun save(like: Like): Like {
        return repository.save(like)
    }

    fun search(
        queryFilter: LikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<LikeOrderType>
    ): List<Like> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: LikeQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions()
        )
    }

    private fun List<LikeOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when(it) {
                LikeOrderType.CREATED_AT_ASC -> like.createdAt.asc()
                LikeOrderType.CREATED_AT_DESC -> like.createdAt.desc()
            }
        }.toTypedArray()
    }
}
