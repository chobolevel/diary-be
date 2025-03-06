package com.daangn.api.categories

import com.daangn.domain.entity.categories.Category
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("카테고리 엔티티 단위 테스트")
class CategoryEntityTest {

    private lateinit var dummyCategory: Category

    @BeforeEach
    fun setup() {
        dummyCategory = DummyCategory.toEntity()
    }

    @Test
    fun `카테고리 엔티티 삭제`() {
        // given

        // when
        dummyCategory.delete()

        // then
        Assertions.assertThat(dummyCategory.deleted).isTrue()
    }
}
