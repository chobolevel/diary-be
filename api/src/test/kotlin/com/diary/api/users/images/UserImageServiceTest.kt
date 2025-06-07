package com.diary.api.users.images

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.service.users.UserImageService
import com.diary.api.service.users.converter.UserImageConverter
import com.diary.api.service.users.updater.UserImageUpdater
import com.diary.api.service.users.validator.UserImageValidator
import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.users.images.UserImage
import com.diary.domain.entity.users.images.UserImageRepositoryWrapper
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
@DisplayName("회원 이미지 서비스 로직 단위 테스트")
class UserImageServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserImage: UserImage = DummyUserImage.toEntity()

    @Mock
    private lateinit var repositoryWrapper: UserImageRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: UserImageConverter

    @Mock
    private lateinit var updater: UserImageUpdater

    @Mock
    private lateinit var validator: UserImageValidator

    @InjectMocks
    private lateinit var service: UserImageService

    @Test
    fun `회원 이미지 등록`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: CreateUserImageRequestDto = DummyUserImage.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyUserImage)
        `when`(userRepositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(userImage = dummyUserImage)).thenReturn(dummyUserImage)

        // when
        val result: ID = service.create(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserImage.id)
    }

    @Test
    fun `회원 이미지 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserImageId: ID = dummyUserImage.id
        val request: UpdateUserImageRequestDto = DummyUserImage.toUpdateRequestDto()
        `when`(
            repositoryWrapper.findByIdAndUserId(
                id = dummyUserImageId,
                userId = dummyUserId
            )
        ).thenReturn(dummyUserImage)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyUserImage
            )
        ).thenReturn(dummyUserImage)

        // when
        val result: ID = service.update(
            userId = dummyUserId,
            userImageId = dummyUserImageId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyUserImageId)
    }

    @Test
    fun `회원 이미지 삭제`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserImageId: ID = dummyUserImage.id
        `when`(
            repositoryWrapper.findByIdAndUserId(
                id = dummyUserImageId,
                userId = dummyUserId
            )
        ).thenReturn(dummyUserImage)

        // when
        val result: Boolean = service.delete(
            userId = dummyUserId,
            userImageId = dummyUserImageId
        )

        // then
        assertThat(result).isTrue()
    }
}
