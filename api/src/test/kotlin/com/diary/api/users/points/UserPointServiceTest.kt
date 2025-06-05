package com.diary.api.users.points

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.dto.users.points.UserPointResponseDto
import com.diary.api.service.users.UserPointService
import com.diary.api.service.users.converter.UserPointConverter
import com.diary.api.service.users.validator.UserPointValidator
import com.diary.api.users.DummyUser
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.entity.users.points.UserPointOrderType
import com.diary.domain.entity.users.points.UserPointQueryFilter
import com.diary.domain.entity.users.points.UserPointRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("회원 포인트 이력 서비스 로직 단위 테스트")
class UserPointServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserPoint: UserPoint = DummyUserPoint.toEntity()
    private val dummyUserPointResponse: UserPointResponseDto = DummyUserPoint.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: UserPointRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: UserPointConverter

    @Mock
    private lateinit var validator: UserPointValidator

    @InjectMocks
    private lateinit var service: UserPointService

    @Test
    fun `회원 포인트 지급`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: AddUserPointRequestDto = DummyUserPoint.toAddUserPointRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyUserPoint)
        `when`(userRepositoryWrapper.findByIdWithLock(id = dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(dummyUserPoint)).thenReturn(dummyUserPoint)

        // when
        val result: ID = service.addPoint(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserPoint.id)
    }

    @Test
    fun `회원 포인트 차감`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: SubUserPointRequestDto = DummyUserPoint.toSubUserPointRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyUserPoint)
        `when`(userRepositoryWrapper.findByIdWithLock(id = dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(dummyUserPoint)).thenReturn(dummyUserPoint)

        // when
        val result: ID = service.subPoint(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserPoint.id)
    }

    @Test
    fun `회원 포인트 내역 목록 조회`() {
        // given
        val queryFilter = UserPointQueryFilter(
            userId = null,
            type = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<UserPointOrderType> = emptyList()
        val dummyUserPoints: List<UserPoint> = listOf(dummyUserPoint)
        val dummyUserPointResponses: List<UserPointResponseDto> = listOf(dummyUserPointResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyUserPoints)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyUserPoints.size.toLong())
        `when`(converter.convert(entities = dummyUserPoints)).thenReturn(dummyUserPointResponses)

        // when
        val result: PaginationResponseDto = service.getUserPoints(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyUserPointResponses)
        assertThat(result.totalCount).isEqualTo(dummyUserPointResponses.size.toLong())
    }
}
