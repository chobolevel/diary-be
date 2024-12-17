package com.scrimmers.domain.entity.user

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.user.QUser.user
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class UserFinder(
    private val repository: UserRepository,
    private val customRepository: UserCustomRepository
) {

    fun existsByEmail(email: String): Boolean {
        return repository.existsByEmail(email)
    }

    fun existsByNickname(nickname: String): Boolean {
        return repository.existsByNickname(nickname)
    }

    fun existsByPhone(phone: String): Boolean {
        return repository.existsByPhone(phone)
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): User {
        return repository.findByIdAndResignedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_IS_NOT_FOUND,
            message = ErrorCode.USER_IS_NOT_FOUND.desc
        )
    }

    @Throws(DataNotFoundException::class)
    fun findByEmailAndLoginType(email: String, loginType: UserLoginType): User {
        return repository.findByEmailAndLoginTypeAndResignedFalse(
            email = email,
            loginType = loginType
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_IS_NOT_FOUND,
            message = ErrorCode.USER_IS_NOT_FOUND.desc
        )
    }

    @Throws(DataNotFoundException::class)
    fun findBySocialIdAndLoginType(socialId: String, loginType: UserLoginType): User {
        return repository.findBySocialIdAndLoginTypeAndResignedFalse(
            socialId = socialId,
            loginType = loginType
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_IS_NOT_FOUND,
            message = ErrorCode.USER_IS_NOT_FOUND.desc
        )
    }

    fun search(queryFilter: UserQueryFilter, pagination: Pagination, orderTypes: List<UserOrderType>?): List<User> {
        val orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderSpecifiers
        )
    }

    fun searchCount(queryFilter: UserQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions()
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<UserOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                UserOrderType.CREATED_AT_ASC -> user.createdAt.asc()
                UserOrderType.CREATED_AT_DESC -> user.createdAt.desc()
            }
        }.toTypedArray()
    }
}
