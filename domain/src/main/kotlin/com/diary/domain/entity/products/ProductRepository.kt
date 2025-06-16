package com.diary.domain.entity.products

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, ID> {

    fun findByIdAndDeletedFalse(id: ID): Product?
}
