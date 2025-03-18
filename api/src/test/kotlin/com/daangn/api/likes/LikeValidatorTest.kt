package com.daangn.api.likes

import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.service.likes.validator.LikeValidator
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
@DisplayName("좋아요 검증기 단위 테스트")
class LikeValidatorTest {

    @Mock
    private lateinit var postRepositoryWrapper: PostRepositoryWrapper

    @InjectMocks
    private lateinit var validator: LikeValidator

    @Test
    fun `좋아요 좋아요 제거 시 좋아요 대상 찾을 수 없는 케이스`() {
        // given
        val request: LikeRequestDto = DummyLike.toRequestDto()
        `when`(postRepositoryWrapper.existsById(request.targetId)).thenReturn(false)

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.LIKE_TARGET_NOT_FOUND)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo(ErrorCode.LIKE_TARGET_NOT_FOUND.message)
    }
}
