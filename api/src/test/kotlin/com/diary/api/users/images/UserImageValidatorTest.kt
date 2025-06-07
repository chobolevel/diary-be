package com.diary.api.users.images

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.service.users.validator.UserImageValidator
import com.diary.domain.entity.users.images.UserImageUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("회원 이미지 validation 단위 테스트")
class UserImageValidatorTest {

    private val validator: UserImageValidator = UserImageValidator()

    @Test
    fun `회원 이미지 등록 시 파일 너비 0 미만인 케이스`() {
        // given
        val request = CreateUserImageRequestDto(
            name = "flow.jpeg",
            width = -1,
            height = 0,
            url = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg",
            order = 1
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[width]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `회원 이미지 등록 시 파일 높이 0 미만인 케이스`() {
        // given
        val request = CreateUserImageRequestDto(
            name = "flow.jpeg",
            width = 0,
            height = -1,
            url = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg",
            order = 1
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[height]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `회원 이미지 등록 시 정렬 순서 1 미만인 케이스`() {
        // given
        val request = CreateUserImageRequestDto(
            name = "flow.jpeg",
            width = 0,
            height = 0,
            url = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg",
            order = 0
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 1 이상이어야 합니다.")
    }

    @Test
    fun `회원 이미지 파일명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.NAME)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 이미지 파일 너비 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.WIDTH)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[width]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 이미지 파일 너비 수정 시 0 미만인 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = -1,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.WIDTH)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[width]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `회원 이미지 파일 높이 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.HEIGHT)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[height]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 이미지 파일 높이 수정 시 0 미만인 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = -1,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.HEIGHT)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[height]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `회원 이미지 파일 경로 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.URL)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[url]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 이미지 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.ORDER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 이미지 정렬 순서 수정 시 1 미만인 케이스`() {
        // given
        val request = UpdateUserImageRequestDto(
            name = null,
            width = null,
            height = null,
            url = null,
            order = 0,
            updateMask = listOf(UserImageUpdateMask.ORDER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 1 이상이어야 합니다.")
    }
}
