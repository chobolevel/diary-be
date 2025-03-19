package com.daangn.api.channels

import com.daangn.api.channels.users.DummyChannelUser
import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.InviteChannelRequestDto
import com.daangn.api.dto.channels.UpdateChannelRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.channels.ChannelService
import com.daangn.api.service.channels.converter.ChannelConverter
import com.daangn.api.service.channels.updater.ChannelUpdater
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.ChannelOrderType
import com.daangn.domain.entity.channels.ChannelQueryFilter
import com.daangn.domain.entity.channels.ChannelRepositoryWrapper
import com.daangn.domain.entity.channels.users.ChannelUser
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("채널 서비스 단위 테스트")
class ChannelServiceTest {

    private lateinit var dummyUser: User

    private lateinit var dummyChannel: Channel
    private lateinit var dummyChannelResponse: ChannelResponseDto

    @Mock
    private lateinit var repositoryWrapper: ChannelRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: ChannelConverter

    @Mock
    private lateinit var updater: ChannelUpdater

    @InjectMocks
    private lateinit var service: ChannelService

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyChannel = DummyChannel.toEntity()
        this.dummyChannelResponse = DummyChannel.toResponseDto()
    }

    @Test
    fun `채널 생성`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: CreateChannelRequestDto = DummyChannel.toCreateRequestDto()
        `when`(converter.convert(request)).thenReturn(dummyChannel)
        `when`(userRepositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(dummyChannel)).thenReturn(dummyChannel)

        // when
        val result: String = service.create(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyChannel.id)
    }

    @Test
    fun `채널 목록 조회`() {
        // given
        val queryFilter = ChannelQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<ChannelOrderType> = emptyList()
        val dummyChannels: List<Channel> = listOf(dummyChannel)
        val dummyChannelResponses: List<ChannelResponseDto> = listOf(dummyChannelResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyChannels)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter,
            )
        ).thenReturn(dummyChannels.size.toLong())
        `when`(converter.convert(dummyChannels)).thenReturn(dummyChannelResponses)

        // when
        val result: PaginationResponseDto = service.getChannels(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyChannelResponses)
        assertThat(result.totalCount).isEqualTo(dummyChannelResponses.size.toLong())
    }

    @Test
    fun `채널 단건 조회`() {
        // given
        val dummyChannelId: String = dummyChannel.id
        `when`(repositoryWrapper.findById(dummyChannelId)).thenReturn(dummyChannel)
        `when`(converter.convert(dummyChannel)).thenReturn(dummyChannelResponse)

        // when
        val result: ChannelResponseDto = service.getChannel(
            channelId = dummyChannelId
        )

        // then
        assertThat(result).isEqualTo(dummyChannelResponse)
    }

    @Test
    fun `채널 정보 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        val request: UpdateChannelRequestDto = DummyChannel.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(dummyChannelId)).thenReturn(dummyChannel)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyChannel
            )
        ).thenReturn(dummyChannel)

        // when
        val result: String = service.update(
            userId = dummyUserId,
            channelId = dummyChannelId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyChannelId)
    }

    @Test
    fun `채널 삭제`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        `when`(repositoryWrapper.findById(dummyChannelId)).thenReturn(dummyChannel)

        // when
        val result: Boolean = service.delete(
            userId = dummyUserId,
            channelId = dummyChannelId
        )

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `채널 초대`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        val dummyUsers: List<User> = listOf(dummyUser)
        val request: InviteChannelRequestDto = DummyChannel.toInviteRequestDto()
        `when`(repositoryWrapper.findById(dummyChannelId)).thenReturn(dummyChannel)
        `when`(userRepositoryWrapper.findByIds(request.userIds)).thenReturn(dummyUsers)

        // when
        val result: Boolean = service.invite(
            userId = dummyUserId,
            channelId = dummyChannelId,
            request = request
        )

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `채널 떠나기`() {
        // given
        val dummyChannelUser: ChannelUser = DummyChannelUser.toEntity()
        dummyChannel.channelUsers.add(dummyChannelUser)
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        `when`(repositoryWrapper.findById(dummyChannelId)).thenReturn(dummyChannel)

        // when
        val result: Boolean = service.leave(
            userId = dummyUserId,
            channelId = dummyChannelId
        )

        // then
        assertThat(result).isTrue()
    }
}
