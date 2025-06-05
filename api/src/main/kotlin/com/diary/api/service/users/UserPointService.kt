package com.diary.api.service.users

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.service.users.converter.UserPointConverter
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.entity.users.points.UserPointOrderType
import com.diary.domain.entity.users.points.UserPointQueryFilter
import com.diary.domain.entity.users.points.UserPointRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPointService(
    private val repositoryWrapper: UserPointRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val converter: UserPointConverter
) {

    @Transactional
    fun addPoint(
        userId: ID,
        request: AddUserPointRequestDto
    ): ID {
        val userPoint: UserPoint = converter.convert(request = request).also { userPoint ->
            // mapping user
            val user: User = userRepositoryWrapper.findByIdWithLock(id = userId)
            userPoint.set(user = user)
            user.addPoint(amount = request.amount)
        }
        return repositoryWrapper.save(userPoint = userPoint).id
    }

    @Transactional
    fun subPoint(
        userId: ID,
        request: SubUserPointRequestDto
    ): ID {
        val userPoint: UserPoint = converter.convert(request = request).also { userPoint ->
            // mapping user
            val user: User = userRepositoryWrapper.findByIdWithLock(id = userId)
            userPoint.set(user = user)
            user.subPoint(amount = request.amount)
        }
        return repositoryWrapper.save(userPoint = userPoint).id
    }

    fun getUserPoints(
        queryFilter: UserPointQueryFilter,
        pagination: Pagination,
        orderTypes: List<UserPointOrderType>
    ): PaginationResponseDto {
        val userPoints: List<UserPoint> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = userPoints),
            totalCount = totalCount
        )
    }
}
