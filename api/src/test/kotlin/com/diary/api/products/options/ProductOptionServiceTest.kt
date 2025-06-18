package com.diary.api.products.options

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.products.DummyProduct
import com.diary.api.products.options.values.DummyProductOptionValue
import com.diary.api.service.products.ProductOptionService
import com.diary.api.service.products.converter.ProductOptionConverter
import com.diary.api.service.products.converter.ProductOptionValueConverter
import com.diary.api.service.products.updater.ProductOptionUpdater
import com.diary.api.service.products.validator.ProductOptionValidator
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductRepositoryWrapper
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionRepositoryWrapper
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
@DisplayName("상품 옵션 서비스 로직 단위 테스트")
class ProductOptionServiceTest {

    private val dummyProduct: Product = DummyProduct.toEntity()

    private val dummyProductOption: ProductOption = DummyProductOption.toEntity()

    private val dummyProductOptionValue: ProductOptionValue = DummyProductOptionValue.toEntity()

    @Mock
    private lateinit var repositoryWrapper: ProductOptionRepositoryWrapper

    @Mock
    private lateinit var productRepositoryWrapper: ProductRepositoryWrapper

    @Mock
    private lateinit var converter: ProductOptionConverter

    @Mock
    private lateinit var productOptionValueConverter: ProductOptionValueConverter

    @Mock
    private lateinit var updater: ProductOptionUpdater

    @Mock
    private lateinit var validator: ProductOptionValidator

    @InjectMocks
    private lateinit var service: ProductOptionService

    @Test
    fun `상품 옵션 등록`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val request: CreateProductOptionRequestDto = DummyProductOption.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyProductOption)
        `when`(productRepositoryWrapper.findById(id = dummyProductId)).thenReturn(dummyProduct)
        `when`(productOptionValueConverter.convert(request = request.values.get(0))).thenReturn(dummyProductOptionValue)
        `when`(repositoryWrapper.save(productOption = dummyProductOption)).thenReturn(dummyProductOption)

        // when
        val result: ID = service.create(
            productId = dummyProductId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductOption.id)
    }

    @Test
    fun `상품 옵션 정보 수정`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val request: UpdateProductOptionRequestDto = DummyProductOption.toUpdateRequestDto()
        `when`(
            repositoryWrapper.findByIdAndProductId(
                id = dummyProductOptionId,
                productId = dummyProductId
            )
        ).thenReturn(dummyProductOption)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyProductOption
            )
        ).thenReturn(dummyProductOption)

        // when
        val result: ID = service.update(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductOptionId)
    }

    @Test
    fun `상품 옵션 삭제`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        `when`(
            repositoryWrapper.findByIdAndProductId(
                id = dummyProductOptionId,
                productId = dummyProductId
            )
        ).thenReturn(dummyProductOption)

        // when
        val result: Boolean = service.delete(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId
        )

        // then
        assertThat(result).isTrue()
    }
}
