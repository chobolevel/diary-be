package com.daangn.domain.entity.users

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.QUser.user
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class UserRepositoryWrapper(
    private val repository: UserRepository,
    private val customRepository: UserCustomRepository
) {

    fun save(user: User): User {
        return repository.save(user)
    }

    fun search(queryFilter: UserQueryFilter, pagination: Pagination, orderTypes: List<UserOrderType>?): List<User> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(queryFilter: UserQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions()
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): User {
        return repository.findByIdAndResignedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_NOT_FOUND,
            message = ErrorCode.USER_NOT_FOUND.message
        )
    }

    @Throws(DataNotFoundException::class)
    fun findByEmail(email: String): User {
        return repository.findByEmailAndResignedFalse(email) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_NOT_FOUND,
            message = ErrorCode.USER_NOT_FOUND.message
        )
    }

    fun existsByEmail(email: String): Boolean {
        return repository.existsByEmailAndResignedFalse(email)
    }

    private fun List<UserOrderType>?.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this?.map {
            when (it) {
                UserOrderType.CREATED_AT_ASC -> user.createdAt.asc()
                UserOrderType.CREATED_AT_DESC -> user.createdAt.desc()
            }
        }?.toTypedArray() ?: emptyArray()
    }
}
