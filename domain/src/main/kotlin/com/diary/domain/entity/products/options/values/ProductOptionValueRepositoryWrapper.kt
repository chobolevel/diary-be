package com.diary.domain.entity.products.options.values

import org.springframework.stereotype.Repository

@Repository
class ProductOptionValueRepositoryWrapper(
    private val repository: ProductOptionValueRepository
) {

    fun save(productOptionValue: ProductOptionValue): ProductOptionValue {
        return repository.save(productOptionValue)
    }
}
