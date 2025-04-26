package com.diary.domain.entity.users

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.QUser.user
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
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

    fun search(
        queryFilter: UserQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserOrderType>
    ): List<User> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(
        queryFilter: UserQueryFilter
    ): Long {
        return customRepository.count(
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
    fun findByUsername(username: String): User {
        return repository.findByUsernameAndResignedFalse(username) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_NOT_FOUND,
            message = ErrorCode.USER_NOT_FOUND.message
        )
    }

    fun existsByUsername(username: String): Boolean {
        return repository.existsByUsernameAndResignedFalse(username)
    }

    private fun List<UserOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                UserOrderType.CREATED_AT_ASC -> user.createdAt.asc()
                UserOrderType.CREATED_AT_DESC -> user.createdAt.desc()
            }
        }.toTypedArray()
    }
}
