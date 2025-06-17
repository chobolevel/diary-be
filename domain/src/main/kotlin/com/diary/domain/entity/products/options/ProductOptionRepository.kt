package com.diary.domain.entity.products.options

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductOptionRepository : JpaRepository<ProductOption, ID>
