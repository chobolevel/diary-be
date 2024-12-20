package com.scrimmers.domain.entity.scrim

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.scrim.QScrim.scrim
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ScrimFinder(
    private val repository: ScrimRepository,
    private val customRepository: ScrimCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Scrim {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.SCRIM_IS_NOT_FOUND,
            message = ErrorCode.SCRIM_IS_NOT_FOUND.desc
        )
    }

    fun search(queryFilter: ScrimQueryFilter, pagination: Pagination, orderTypes: List<ScrimOrderType>?): List<Scrim> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: ScrimQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<ScrimOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                ScrimOrderType.CREATED_AT_ASC -> scrim.createdAt.asc()
                ScrimOrderType.CREATED_AT_DESC -> scrim.createdAt.desc()
            }
        }.toTypedArray()
    }
}
