package com.diary.api.products.options

import com.diary.api.products.DummyProduct
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.options.ProductOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("상품 옵션 엔티티 단위 테스트")
class ProductOptionEntityTest {

    private val dummyProductOption: ProductOption = DummyProductOption.toEntity()

    @Test
    fun `상품 옵션 엔티티 상품 엔티티 매핑`() {
        // given
        val dummyProduct: Product = DummyProduct.toEntity()

        // when
        dummyProductOption.set(product = dummyProduct)

        // then
        assertThat(dummyProductOption.product).isEqualTo(dummyProduct)
    }

    @Test
    fun `상품 옵션 엔티티 삭제`() {
        // given

        // when
        dummyProductOption.delete()

        // then
        assertThat(dummyProductOption.deleted).isTrue()
    }
}
