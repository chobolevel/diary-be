package com.diary.api.products.variants

import com.diary.api.products.DummyProduct
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.variants.ProductVariant
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("상품 변형 엔티티 단위 테스트")
class ProductVariantEntityTest {

    private val dummyProductVariant: ProductVariant = DummyProductVariant.toEntity()

    @Test
    fun `상품 변형 엔티티 상품 엔티티 매핑`() {
        // given
        val dummyProduct: Product = DummyProduct.toEntity()

        // when
        dummyProductVariant.set(product = dummyProduct)

        // then
        assertThat(dummyProductVariant.product).isEqualTo(dummyProduct)
    }

    @Test
    fun `상품 변형 엔티티 삭제`() {
        // given

        // when
        dummyProductVariant.delete()

        // then
        assertThat(dummyProductVariant.deleted).isTrue()
    }
}
