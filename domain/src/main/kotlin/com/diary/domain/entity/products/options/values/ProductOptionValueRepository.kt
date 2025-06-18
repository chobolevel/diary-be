package com.diary.domain.entity.products.options.values

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductOptionValueRepository : JpaRepository<ProductOptionValue, ID> {

    @Query("SELECT pov FROM ProductOptionValue pov WHERE pov.id = :id AND pov.productOption.product.id = :productId AND pov.productOption.id = :productOptionId AND pov.deleted = false")
    fun findByIdAndProductIdAndProductOptionIdAndDeletedFalse(
        id: ID,
        productId: ID,
        productOptionId: ID,
    ): ProductOptionValue?
}
