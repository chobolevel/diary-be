package com.daangn.api.users.regions

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UserRegionResponseDto
import com.daangn.api.service.users.UserRegionService
import com.daangn.api.service.users.converter.UserRegionConverter
import com.daangn.api.service.users.updater.UserRegionUpdater
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.entity.users.regions.UserRegion
import com.daangn.domain.entity.users.regions.UserRegionOrderType
import com.daangn.domain.entity.users.regions.UserRegionQueryFilter
import com.daangn.domain.entity.users.regions.UserRegionRepositoryWrapper
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
@DisplayName("회원 지역 서비스 단위 테스트")
class UserRegionServiceTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserRegion: UserRegion
    private lateinit var dummyUserRegionResponse: UserRegionResponseDto

    @Mock
    private lateinit var repositoryWrapper: UserRegionRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: UserRegionConverter

    @Mock
    private lateinit var updater: UserRegionUpdater

    @InjectMocks
    private lateinit var service: UserRegionService

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyUserRegion = DummyUserRegion.toEntity()
        dummyUserRegionResponse = DummyUserRegion.toResponseDto()
    }

    @Test
    fun `회원 지역 등록`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: CreateUserRegionRequestDto = DummyUserRegion.toCreateRequestDto()
        `when`(
            converter.convert(
                request = request
            )
        ).thenReturn(dummyUserRegion)
        `when`(userRepositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(
            repositoryWrapper.save(
                userRegion = dummyUserRegion
            )
        ).thenReturn(dummyUserRegion)

        // when
        val result: String = service.create(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserRegion.id)
    }

    @Test
    fun `회원 지역 목록 조회`() {
        // given
        val queryFilter = UserRegionQueryFilter(
            userId = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserRegionOrderType> = emptyList()
        val dummyUserRegions: List<UserRegion> = listOf(
            dummyUserRegion
        )
        val dummyUserRegionResponses: List<UserRegionResponseDto> = listOf(
            dummyUserRegionResponse
        )
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyUserRegions)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter,
            )
        ).thenReturn(dummyUserRegions.size.toLong())
        `when`(converter.convert(dummyUserRegions)).thenReturn(dummyUserRegionResponses)

        // when
        val result: PaginationResponseDto = service.getUserRegions(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyUserRegionResponses)
        assertThat(result.totalCount).isEqualTo(dummyUserRegionResponses.size.toLong())
    }

    @Test
    fun `회원 지역 단건 조회`() {
        // given
        val dummyUserRegionId: String = dummyUserRegion.id
        `when`(repositoryWrapper.findById(dummyUserRegionId)).thenReturn(dummyUserRegion)
        `when`(converter.convert(dummyUserRegion)).thenReturn(dummyUserRegionResponse)

        // when
        val result: UserRegionResponseDto = service.getUserRegion(
            userRegionId = dummyUserRegionId
        )

        // then
        assertThat(result).isEqualTo(dummyUserRegionResponse)
    }

    @Test
    fun `회원 지역 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyUserRegionId: String = dummyUserRegion.id
        val request: UpdateUserRegionRequestDto = DummyUserRegion.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(dummyUserRegionId)).thenReturn(dummyUserRegion)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyUserRegion
            )
        ).thenReturn(dummyUserRegion)

        // when
        val result: String = service.update(
            userId = dummyUserId,
            userRegionId = dummyUserRegionId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserRegionId)
    }

    @Test
    fun `회원 지역 삭제`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyUserRegionId: String = dummyUserRegion.id
        `when`(repositoryWrapper.findById(dummyUserRegionId)).thenReturn(dummyUserRegion)

        // when
        val result: Boolean = service.delete(
            userId = dummyUserId,
            userRegionId = dummyUserRegionId
        )

        // then
        assertThat(result).isTrue()
    }
}
