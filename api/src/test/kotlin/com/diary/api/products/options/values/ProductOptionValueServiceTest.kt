package com.diary.api.products.options.values

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.api.dto.products.options.values.UpdateProductOptionValueRequestDto
import com.diary.api.products.DummyProduct
import com.diary.api.products.options.DummyProductOption
import com.diary.api.service.products.ProductOptionValueService
import com.diary.api.service.products.converter.ProductOptionValueConverter
import com.diary.api.service.products.updater.ProductOptionValueUpdater
import com.diary.api.service.products.validator.ProductOptionValueValidator
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionRepositoryWrapper
import com.diary.domain.entity.products.options.values.ProductOptionValue
import com.diary.domain.entity.products.options.values.ProductOptionValueRepositoryWrapper
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
@DisplayName("상품 옵션 값 서비스 로직 단위 테스트")
class ProductOptionValueServiceTest {

    private val dummyProduct: Product = DummyProduct.toEntity()

    private val dummyProductOption: ProductOption = DummyProductOption.toEntity()

    private val dummyProductOptionValue: ProductOptionValue = DummyProductOptionValue.toEntity()

    @Mock
    private lateinit var repositoryWrapper: ProductOptionValueRepositoryWrapper

    @Mock
    private lateinit var productOptionRepositoryWrapper: ProductOptionRepositoryWrapper

    @Mock
    private lateinit var converter: ProductOptionValueConverter

    @Mock
    private lateinit var updater: ProductOptionValueUpdater

    @Mock
    private lateinit var validator: ProductOptionValueValidator

    @InjectMocks
    private lateinit var service: ProductOptionValueService

    @Test
    fun `상품 옵션 값 등록`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val dummyProductOptionValueId: ID = dummyProductOptionValue.id
        val request: CreateProductOptionValueRequestDto = DummyProductOptionValue.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyProductOptionValue)
        `when`(
            productOptionRepositoryWrapper.findByIdAndProductId(
                id = dummyProductOptionId,
                productId = dummyProductId
            )
        ).thenReturn(dummyProductOption)
        `when`(repositoryWrapper.save(productOptionValue = dummyProductOptionValue)).thenReturn(dummyProductOptionValue)

        // when
        val result: ID = service.create(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductOptionValueId)
    }

    @Test
    fun `상품 옵션 값 정보 수정`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val dummyProductOptionValueId: ID = dummyProductOptionValue.id
        val request: UpdateProductOptionValueRequestDto = DummyProductOptionValue.toUpdateRequestDto()
        `when`(
            repositoryWrapper.findByIdProductIdAndProductOptionId(
                id = dummyProductOptionValueId,
                productId = dummyProductId,
                productOptionId = dummyProductOptionId
            )
        ).thenReturn(dummyProductOptionValue)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyProductOptionValue
            )
        ).thenReturn(dummyProductOptionValue)

        // when
        val result: ID = service.update(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
            productOptionValueId = dummyProductOptionValueId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyProductOptionValueId)
    }

    @Test
    fun `상품 옵션 값 삭제`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val dummyProductOptionValueId: ID = dummyProductOptionValue.id
        `when`(
            repositoryWrapper.findByIdProductIdAndProductOptionId(
                id = dummyProductOptionValueId,
                productId = dummyProductId,
                productOptionId = dummyProductOptionId
            )
        ).thenReturn(dummyProductOptionValue)

        // when
        val result: Boolean = service.delete(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
            productOptionValueId = dummyProductOptionValueId
        )

        // then
        assertThat(result).isTrue()
    }
}
