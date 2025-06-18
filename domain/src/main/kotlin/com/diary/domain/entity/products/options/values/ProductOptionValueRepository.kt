package com.diary.domain.entity.products.options.values

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductOptionValueRepository : JpaRepository<ProductOptionValue, ID>
