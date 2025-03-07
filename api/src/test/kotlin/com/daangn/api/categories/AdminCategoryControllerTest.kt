package com.daangn.api.categories

import com.daangn.api.controller.v1.admin.categories.AdminCategoryController
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.categories.CategoryService
import com.daangn.domain.entity.categories.Category
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

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 카테고리 컨트롤러 단위 테스트")
class AdminCategoryControllerTest {

    private lateinit var dummyCategory: Category

    @Mock
    private lateinit var service: CategoryService

    @InjectMocks
    private lateinit var controller: AdminCategoryController

    @BeforeEach
    fun setup() {
        dummyCategory = DummyCategory.toEntity()
    }

    @Test
    fun `카테고리 등록`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        val request: CreateCategoryRequestDto = DummyCategory.toCreateRequestDto()
        `when`(
            service.create(
                request = request
            )
        ).thenReturn(dummyCategoryId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyCategoryId)
    }

    @Test
    fun `카테고리 수정`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        val request: UpdateCategoryRequestDto = DummyCategory.toUpdateRequestDto()
        `when`(
            service.update(
                categoryId = dummyCategoryId,
                request = request
            )
        ).thenReturn(dummyCategoryId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            categoryId = dummyCategoryId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyCategoryId)
    }

    @Test
    fun `카테고리 삭제`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        `when`(
            service.delete(
                categoryId = dummyCategoryId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            categoryId = dummyCategoryId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
