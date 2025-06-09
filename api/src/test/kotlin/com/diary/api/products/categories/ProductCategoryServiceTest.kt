package com.diary.api.products.categories

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.service.products.ProductCategoryService
import com.diary.api.service.products.converter.ProductCategoryConverter
import com.diary.api.service.products.updater.ProductCategoryUpdater
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryOrderType
import com.diary.domain.entity.products.categories.ProductCategoryQueryFilter
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
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
@DisplayName("상품 카테고리 서비스 로직 단위 테스트")
class ProductCategoryServiceTest {

    private val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()
    private val dummyProductCategoryResponse: ProductCategoryResponseDto = DummyProductCategory.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: ProductCategoryRepositoryWrapper

    @Mock
    private lateinit var converter: ProductCategoryConverter

    @Mock
    private lateinit var updater: ProductCategoryUpdater

    @InjectMocks
    private lateinit var service: ProductCategoryService

    @Test
    fun `상품 카테고리 등록`() {
        // given
        val request: CreateProductCategoryRequestDto = DummyProductCategory.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyProductCategory)
        `when`(repositoryWrapper.findById(id = request.parentId!!)).thenReturn(dummyProductCategory)
        `when`(repositoryWrapper.save(productCategory = dummyProductCategory)).thenReturn(dummyProductCategory)

        // when
        val result: ID = service.create(request = request)

        // then
        assertThat(result).isEqualTo(dummyProductCategory.id)
    }

    @Test
    fun `상품 카테고리 목록 조회`() {
        // given
        val queryFilter = ProductCategoryQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<ProductCategoryOrderType> = emptyList()
        val dummyProductCategories: List<ProductCategory> = listOf(dummyProductCategory)
        val dummyProductCategoryResponses: List<ProductCategoryResponseDto> = listOf(dummyProductCategoryResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyProductCategories)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyProductCategories.size.toLong())
        `when`(converter.convert(entities = dummyProductCategories)).thenReturn(dummyProductCategoryResponses)

        // when
        val result: PaginationResponseDto = service.getProductCategories(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyProductCategoryResponses)
        assertThat(result.totalCount).isEqualTo(dummyProductCategoryResponses.size.toLong())
    }

    @Test
    fun `상품 카테고리 단건 조회`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        `when`(repositoryWrapper.findById(id = dummyProductCategoryId)).thenReturn(dummyProductCategory)
        `when`(converter.convert(entity = dummyProductCategory)).thenReturn(dummyProductCategoryResponse)

        // when
        val result: ProductCategoryResponseDto = service.getProductCategory(productCategoryId = dummyProductCategoryId)

        // then
        assertThat(result).isEqualTo(dummyProductCategoryResponse)
    }

    @Test
    fun `상품 카테고리 정보 수정`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        val request: UpdateProductCategoryRequestDto = DummyProductCategory.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyProductCategoryId)).thenReturn(dummyProductCategory)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyProductCategory
            )
        ).thenReturn(dummyProductCategory)

        // when
        val result: ID = service.update(
            productCategoryId = dummyProductCategoryId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductCategoryId)
    }

    @Test
    fun `상품 카테고리 삭제`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        `when`(repositoryWrapper.findById(id = dummyProductCategoryId)).thenReturn(dummyProductCategory)

        // when
        val result: Boolean = service.delete(productCategoryId = dummyProductCategoryId)

        // then
        assertThat(result).isTrue()
    }
}
