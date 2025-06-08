package com.diary.domain.entity.products.categories

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCategoryRepository : JpaRepository<ProductCategory, ID> {

    fun findByIdAndDeletedFalse(id: ID): ProductCategory?
}
