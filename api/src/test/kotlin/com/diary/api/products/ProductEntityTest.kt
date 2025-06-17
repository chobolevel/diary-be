package com.diary.api.products

import com.diary.api.products.categories.DummyProductCategory
import com.diary.api.products.options.DummyProductOption
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.options.ProductOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("상품 엔티티 단위 테스트")
class ProductEntityTest {

    private val dummyProduct: Product = DummyProduct.toEntity()

    @Test
    fun `상품 엔티티 상품 카테고리 엔티티 매핑`() {
        // given
        val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()

        // when
        dummyProduct.set(productCategory = dummyProductCategory)

        // then
        assertThat(dummyProduct.productCategory).isEqualTo(dummyProductCategory)
    }

    @Test
    fun `상품 엔티티 삭제`() {
        // given

        // when
        dummyProduct.delete()

        // then
        assertThat(dummyProduct.deleted).isTrue()
    }

    @Test
    fun `상품 엔티티 상품 옵션 엔티티 매핑`() {
        // given
        val dummyProductOption: ProductOption = DummyProductOption.toEntity()

        // when
        dummyProduct.add(productOption = dummyProductOption)

        // then
        assertThat(dummyProduct.productOptions.get(0)).isEqualTo(dummyProductOption)
    }
}
