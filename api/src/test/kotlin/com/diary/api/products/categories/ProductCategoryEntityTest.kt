package com.diary.api.products.categories

import com.diary.domain.entity.products.categories.ProductCategory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("상품 카테고리 엔티티 단위 테스트")
class ProductCategoryEntityTest {

    private val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()

    @Test
    fun `상품 카테고리 엔티티 부모 카테고리 엔티티 매핑`() {
        // given
        val dummyParentProductCategory: ProductCategory = DummyProductCategory.toParentEntity()

        // when
        dummyProductCategory.set(parent = dummyParentProductCategory)

        // then
        assertThat(dummyProductCategory.parent).isEqualTo(dummyParentProductCategory)
    }

    @Test
    fun `상품 카테고리 엔티티 삭제`() {
        // given

        // when
        dummyProductCategory.delete()

        // then
        assertThat(dummyProductCategory.deleted).isTrue()
    }
}
