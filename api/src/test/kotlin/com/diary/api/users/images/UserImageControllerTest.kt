package com.diary.api.users.images

import com.diary.api.controller.v1.common.users.UserImageController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.service.users.UserImageService
import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.images.UserImage
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
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
@DisplayName("회원 이미지 컨트롤러 단위 테스트")
class UserImageControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserToken: UsernamePasswordAuthenticationToken = DummyUser.toToken()

    private val dummyUserImage: UserImage = DummyUserImage.toEntity()

    @Mock
    private lateinit var service: UserImageService

    @InjectMocks
    private lateinit var controller: UserImageController

    @Test
    fun `회원 이미지 등록`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserImageId: ID = dummyUserImage.id
        val request: CreateUserImageRequestDto = DummyUserImage.toCreateRequestDto()
        `when`(
            service.create(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserImageId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyUserImageId)
    }

    @Test
    fun `회원 이미지 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserImageId: ID = dummyUserImage.id
        val request: UpdateUserImageRequestDto = DummyUserImage.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
                userImageId = dummyUserImageId,
                request = request
            )
        ).thenReturn(dummyUserImageId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            principal = dummyUserToken,
            userImageId = dummyUserImageId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserImageId)
    }

    @Test
    fun `회원 이미지 삭제`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserImageId: ID = dummyUserImage.id
        `when`(
            service.delete(
                userId = dummyUserId,
                userImageId = dummyUserImageId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            principal = dummyUserToken,
            userImageId = dummyUserImageId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
