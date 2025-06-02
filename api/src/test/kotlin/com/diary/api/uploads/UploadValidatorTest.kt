package com.diary.api.uploads

import com.diary.api.service.uploads.validator.UploadValidator
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("업로드 validation 단위 테스트")
class UploadValidatorTest {

    private val validator: UploadValidator = UploadValidator()

    @Test
    fun `presgined url 발급 시 허용되지 않는 content type 케이스`() {
        // given
        val contentType: String = "application/pdf"
        val filename: String = "flow.jpeg"

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(
                contentType = contentType,
                filename = filename
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content_type]은(는) image/jpeg, image/png, image/gif, image/webp만 가능합니다.")
    }
}
