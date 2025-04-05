package com.daangn.api.service.channels

import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.InviteChannelRequestDto
import com.daangn.api.dto.channels.UpdateChannelRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.channels.converter.ChannelConverter
import com.daangn.api.service.channels.updater.ChannelUpdater
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.ChannelOrderType
import com.daangn.domain.entity.channels.ChannelQueryFilter
import com.daangn.domain.entity.channels.ChannelRepositoryWrapper
import com.daangn.domain.entity.channels.users.ChannelUser
import com.daangn.domain.entity.channels.users.ChannelUserGrade
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(
    private val repositoryWrapper: ChannelRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val converter: ChannelConverter,
    private val updater: ChannelUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateChannelRequestDto): String {
        val channel = converter.convert(request).also { channel ->
            // set master
            ChannelUser(
                id = TSID.fast().toString(),
                grade = ChannelUserGrade.MASTER
            ).also { masterChannelUser ->
                masterChannelUser.set(channel)
                masterChannelUser.set(userRepositoryWrapper.findById(userId))
            }

            // set channel users
            val users: List<User> = userRepositoryWrapper.findByIds(request.userIds)
            users.forEach { user ->
                ChannelUser(
                    id = TSID.fast().toString(),
                    grade = ChannelUserGrade.GENERAL
                ).also { channelUser ->
                    channelUser.set(channel)
                    channelUser.set(user)
                }
            }
        }
        return repositoryWrapper.save(channel).id
    }

    @Transactional(readOnly = true)
    fun getChannels(
        queryFilter: ChannelQueryFilter,
        pagination: Pagination,
        orderTypes: List<ChannelOrderType>
    ): PaginationResponseDto {
        val channels = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter,
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(channels),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getChannel(channelId: String): ChannelResponseDto {
        val channel = repositoryWrapper.findById(channelId)
        return converter.convert(channel)
    }

    @Transactional
    fun update(userId: String, channelId: String, request: UpdateChannelRequestDto): String {
        val channel = repositoryWrapper.findById(channelId)
        updater.markAsUpdate(
            request = request,
            entity = channel
        )
        return channelId
    }

    @Transactional
    fun delete(userId: String, channelId: String): Boolean {
        val channel = repositoryWrapper.findById(channelId)
        channel.delete()
        return true
    }

    @Transactional
    fun invite(userId: String, channelId: String, request: InviteChannelRequestDto): Boolean {
        val channel = repositoryWrapper.findById(channelId)
        val users = userRepositoryWrapper.findByIds(request.userIds)
        users.forEach { user ->
            ChannelUser(
                id = TSID.fast().toString(),
                grade = ChannelUserGrade.GENERAL
            ).also { channelUser ->
                channelUser.set(channel)
                channelUser.set(user)
            }
        }
        return true
    }

    @Transactional
    fun leave(userId: String, channelId: String): Boolean {
        val channel = repositoryWrapper.findById(channelId)
        val channelUser = channel.channelUsers.find { it.user!!.id == userId } ?: throw InvalidParameterException(
            errorCode = ErrorCode.ALREADY_LEAVED_CHANNEL,
            message = ErrorCode.ALREADY_LEAVED_CHANNEL.message
        )
        channelUser.delete()
        return true
    }
}
