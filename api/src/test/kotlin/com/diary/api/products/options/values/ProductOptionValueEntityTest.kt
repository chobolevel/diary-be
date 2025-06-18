package com.diary.api.products.options.values

import com.diary.api.products.options.DummyProductOption
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.values.ProductOptionValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("상품 옵션 값 엔티티 단위 테스트")
class ProductOptionValueEntityTest {

    private val dummyProductOptionValue: ProductOptionValue = DummyProductOptionValue.toEntity()

    @Test
    fun `상품 옵션 값 엔티티 상품 옵션 엔티티 매핑`() {
        // given
        val dummyProductOption: ProductOption = DummyProductOption.toEntity()

        // when
        dummyProductOptionValue.set(productOption = dummyProductOption)

        // then
        assertThat(dummyProductOptionValue.productOption).isEqualTo(dummyProductOption)
    }

    @Test
    fun `상품 옵션 값 엔티티 삭제`() {
        // given

        // when
        dummyProductOptionValue.delete()

        // then
        assertThat(dummyProductOptionValue.deleted).isTrue()
    }
}
