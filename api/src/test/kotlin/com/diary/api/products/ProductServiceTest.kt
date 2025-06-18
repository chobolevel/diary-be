package com.diary.api.products

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.products.categories.DummyProductCategory
import com.diary.api.products.options.DummyProductOption
import com.diary.api.products.options.values.DummyProductOptionValue
import com.diary.api.service.products.ProductService
import com.diary.api.service.products.converter.ProductConverter
import com.diary.api.service.products.converter.ProductOptionConverter
import com.diary.api.service.products.converter.ProductOptionValueConverter
import com.diary.api.service.products.updater.ProductUpdater
import com.diary.api.service.products.validator.ProductValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductOrderType
import com.diary.domain.entity.products.ProductQueryFilter
import com.diary.domain.entity.products.ProductRepositoryWrapper
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.values.ProductOptionValue
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
@DisplayName("상품 서비스 로직 단위 테스트")
class ProductServiceTest {

    private val dummyProduct: Product = DummyProduct.toEntity()
    private val dummyProductResponse: ProductResponseDto = DummyProduct.toResponseDto()

    private val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()

    private val dummyProductOption: ProductOption = DummyProductOption.toEntity()

    private val dummyProductOptionValue: ProductOptionValue = DummyProductOptionValue.toEntity()

    @Mock
    private lateinit var repositoryWrapper: ProductRepositoryWrapper

    @Mock
    private lateinit var productCategoryRepositoryWrapper: ProductCategoryRepositoryWrapper

    @Mock
    private lateinit var converter: ProductConverter

    @Mock
    private lateinit var productOptionConverter: ProductOptionConverter

    @Mock
    private lateinit var productOptionValueConverter: ProductOptionValueConverter

    @Mock
    private lateinit var updater: ProductUpdater

    @Mock
    private lateinit var validator: ProductValidator

    @InjectMocks
    private lateinit var service: ProductService

    @Test
    fun `상품 등록`() {
        // given
        val request: CreateProductRequestDto = DummyProduct.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyProduct)
        `when`(productCategoryRepositoryWrapper.findById(id = request.productCategoryId)).thenReturn(dummyProductCategory)
        `when`(productOptionConverter.convert(request.options.get(0))).thenReturn(dummyProductOption)
        `when`(productOptionValueConverter.convert(request = request.options.get(0).values.get(0))).thenReturn(dummyProductOptionValue)
        `when`(repositoryWrapper.save(product = dummyProduct)).thenReturn(dummyProduct)

        // when
        val result: ID = service.create(request = request)

        // then
        assertThat(result).isEqualTo(dummyProduct.id)
    }

    @Test
    fun `상품 목록 조회`() {
        // given
        val queryFilter = ProductQueryFilter(
            productCategoryId = null,
            status = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<ProductOrderType> = emptyList()
        val dummyProducts: List<Product> = listOf(dummyProduct)
        val dummyProductResponses: List<ProductResponseDto> = listOf(dummyProductResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyProducts)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyProducts.size.toLong())
        `when`(converter.convert(entities = dummyProducts)).thenReturn(dummyProductResponses)

        // when
        val result: PaginationResponseDto = service.getProducts(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyProductResponses)
        assertThat(result.totalCount).isEqualTo(dummyProductResponses.size.toLong())
    }

    @Test
    fun `상품 단건 조회`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        `when`(repositoryWrapper.findById(id = dummyProductId)).thenReturn(dummyProduct)
        `when`(converter.convert(entity = dummyProduct)).thenReturn(dummyProductResponse)

        // when
        val result: ProductResponseDto = service.getProduct(productId = dummyProductId)

        // then
        assertThat(result).isEqualTo(dummyProductResponse)
    }

    @Test
    fun `상품 정보 수정`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val request: UpdateProductRequestDto = DummyProduct.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyProductId)).thenReturn(dummyProduct)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyProduct
            )
        ).thenReturn(dummyProduct)

        // when
        val result: ID = service.update(
            productId = dummyProductId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductId)
    }

    @Test
    fun `상품 삭제`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        `when`(repositoryWrapper.findById(id = dummyProductId)).thenReturn(dummyProduct)

        // when
        val result: Boolean = service.delete(productId = dummyProductId)

        // then
        assertThat(result).isTrue()
    }
}
