package com.daangn.api.channels

import com.daangn.api.controller.v1.common.channels.ChannelController
import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.InviteChannelRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.channels.ChannelService
import com.daangn.api.service.channels.query.ChannelQueryCreator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.ChannelOrderType
import com.daangn.domain.entity.channels.ChannelQueryFilter
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@ExtendWith(MockitoExtension::class)
@DisplayName("채널 컨트롤러 단위 테스트")
class ChannelControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyChannel: Channel
    private lateinit var dummyChannelResponse: ChannelResponseDto

    @Mock
    private lateinit var service: ChannelService

    @Mock
    private lateinit var queryCreator: ChannelQueryCreator

    @InjectMocks
    private lateinit var controller: ChannelController

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyUserToken = DummyUser.toToken()
        this.dummyChannel = DummyChannel.toEntity()
        this.dummyChannelResponse = DummyChannel.toResponseDto()
    }

    @Test
    fun `채널 생성`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        val request: CreateChannelRequestDto = DummyChannel.toCreateRequestDto()
        `when`(
            service.create(
                userId = dummyUserId,
                request = request,
            )
        ).thenReturn(dummyChannelId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyChannelId)
    }

    @Test
    fun `채널 목록 조회`() {
        // given
        val queryFilter = ChannelQueryFilter(
            name = null,
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<ChannelOrderType> = emptyList()
        val dummyChannelResponses: List<ChannelResponseDto> = listOf(dummyChannelResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyChannelResponses,
            totalCount = dummyChannelResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                name = null
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null,
            )
        ).thenReturn(pagination)
        `when`(
            service.getChannels(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getChannels(
            name = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }

    @Test
    fun `채널 단건 조회`() {
        // given
        val dummyChannelId: String = dummyChannel.id
        `when`(
            service.getChannel(
                channelId = dummyChannelId,
            )
        ).thenReturn(dummyChannelResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getChannel(
            channelId = dummyChannelId,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyChannelResponse)
    }

    @Test
    fun `채널 초대`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        val request: InviteChannelRequestDto = DummyChannel.toInviteRequestDto()
        `when`(
            service.invite(
                userId = dummyUserId,
                channelId = dummyChannelId,
                request = request
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.invite(
            principal = dummyUserToken,
            channelId = dummyChannelId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `채널 떠나기`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyChannelId: String = dummyChannel.id
        `when`(
            service.leave(
                userId = dummyUserId,
                channelId = dummyChannelId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.leave(
            principal = dummyUserToken,
            channelId = dummyChannelId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
