package com.diary.api.users

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.api.service.users.converter.UserConverter
import com.diary.api.service.users.updater.UserUpdater
import com.diary.api.service.users.validator.UserValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserOrderType
import com.diary.domain.entity.users.UserQueryFilter
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
@DisplayName("회원 서비스 로직 단위 테스트")
class UserServiceTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserResponse: UserResponseDto = DummyUser.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: UserConverter

    @Mock
    private lateinit var updater: UserUpdater

    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Mock
    private lateinit var validator: UserValidator

    @InjectMocks
    private lateinit var service: UserService

    @Test
    fun `회원 가입`() {
        // given
        val request: CreateUserRequestDto = DummyUser.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(user = dummyUser)).thenReturn(dummyUser)

        // when
        val result: ID = service.join(
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }

    @Test
    fun `회원 목록 조회`() {
        // given
        val queryFilter = UserQueryFilter(
            username = null,
            signUpType = null,
            nickname = null,
            resigned = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserOrderType> = emptyList()
        val dummyUsers: List<User> = listOfNotNull(dummyUser)
        val dummyUserResponses: List<UserResponseDto> = listOfNotNull(dummyUserResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyUsers)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyUsers.size.toLong())
        `when`(converter.convert(entities = dummyUsers)).thenReturn(dummyUserResponses)

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
        assertThat(result.totalCount).isEqualTo(dummyUsers.size.toLong())
    }

    @Test
    fun `회원 단건 조회`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(repositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(converter.convert(entity = dummyUser)).thenReturn(dummyUserResponse)

        // when
        val result: UserResponseDto = service.getUser(id = dummyUserId)

        // then
        assertThat(result).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `회원 본인 정보 조회`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(repositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(converter.convert(entity = dummyUser)).thenReturn(dummyUserResponse)

        // when
        val result: UserResponseDto = service.getMe(id = dummyUserId)

        // then
        assertThat(result).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `회원 정보 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: UpdateUserRequestDto = DummyUser.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyUser
            )
        ).thenReturn(dummyUser)

        // when
        val result: ID = service.update(
            id = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }

    @Test
    fun `회원 탈퇴`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(repositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)

        // when
        val result: Boolean = service.resign(id = dummyUserId)

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `비밀번호 변경`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: ChangeUserPasswordRequestDto = DummyUser.toChangePasswordRequestDto()
        `when`(repositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(
            passwordEncoder.matches(
                request.curPassword,
                dummyUser.password
            )
        ).thenReturn(true)
        `when`(passwordEncoder.encode(request.newPassword)).thenReturn(request.newPassword)

        // when
        val result: ID = service.changePassword(
            id = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }
}
