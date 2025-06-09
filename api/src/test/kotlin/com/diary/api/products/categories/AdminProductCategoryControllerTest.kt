package com.diary.api.products.categories

import com.diary.api.controller.v1.admin.products.AdminProductCategoryController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.service.products.ProductCategoryService
import com.diary.domain.entity.products.categories.ProductCategory
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

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 상품 카테고리 컨트롤러 단위 테스트")
class AdminProductCategoryControllerTest {

    private val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()

    @Mock
    private lateinit var service: ProductCategoryService

    @InjectMocks
    private lateinit var controller: AdminProductCategoryController

    @Test
    fun `상품 카테고리 등록`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        val request: CreateProductCategoryRequestDto = DummyProductCategory.toCreateRequestDto()
        `when`(service.create(request = request)).thenReturn(dummyProductCategoryId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(request = request)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyProductCategoryId)
    }

    @Test
    fun `상품 카테고리 수정`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        val request: UpdateProductCategoryRequestDto = DummyProductCategory.toUpdateRequestDto()
        `when`(
            service.update(
                productCategoryId = dummyProductCategoryId,
                request = request
            )
        ).thenReturn(dummyProductCategoryId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            productCategoryId = dummyProductCategoryId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyProductCategoryId)
    }

    @Test
    fun `상품 카테고리 삭제`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        `when`(service.delete(productCategoryId = dummyProductCategoryId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(productCategoryId = dummyProductCategoryId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
