package com.daangn.api.users

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.api.service.users.UserService
import com.daangn.api.service.users.converter.UserConverter
import com.daangn.api.service.users.updater.UserUpdater
import com.daangn.api.service.users.validator.UserValidator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserOrderType
import com.daangn.domain.entity.users.UserQueryFilter
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
@DisplayName("회원 서비스 단위 테스트")
class UserServiceTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserResponse: UserResponseDto

    @Mock
    private lateinit var repositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: UserConverter

    @Mock
    private lateinit var updater: UserUpdater

    @Mock
    private lateinit var validator: UserValidator

    @InjectMocks
    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyUserResponse = DummyUser.toResponseDto()
    }

    @Test
    fun `회원가입`() {
        // given
        val request: CreateUserRequestDto = DummyUser.toCreateRequestDto()
        `when`(converter.convert(request)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(dummyUser)).thenReturn(dummyUser)

        // when
        val result: String = service.create(request)

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }

    @Test
    fun `회원 목록 조회`() {
        // given
        val queryFilter = UserQueryFilter(
            signUpType = null,
            role = null,
            resigned = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserOrderType> = emptyList()
        val dummyUsers: List<User> = listOf(dummyUser)
        val dummyUserResponses: List<UserResponseDto> = listOf(dummyUserResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyUsers)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter,
            )
        ).thenReturn(dummyUsers.size.toLong())
        `when`(converter.convert(dummyUsers)).thenReturn(dummyUserResponses)

        // when
        val result: PaginationResponseDto = service.getUsers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyUserResponses)
        assertThat(result.totalCount).isEqualTo(dummyUserResponses.size.toLong())
    }

    @Test
    fun `회원 단건 조회`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(
            repositoryWrapper.findById(
                dummyUserId
            )
        ).thenReturn(dummyUser)
        `when`(converter.convert(dummyUser)).thenReturn(dummyUserResponse)

        // when
        val result: UserResponseDto = service.getUser(
            userId = dummyUserId
        )

        // then
        assertThat(result).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `회원 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: UpdateUserRequestDto = DummyUser.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyUser
            )
        ).thenReturn(dummyUser)

        // when
        val result: String = service.update(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }

    @Test
    fun `회원 탈퇴`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(repositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)

        // when
        val result: Boolean = service.resign(
            userId = dummyUserId
        )

        // then
        assertThat(result).isTrue()
    }
}
