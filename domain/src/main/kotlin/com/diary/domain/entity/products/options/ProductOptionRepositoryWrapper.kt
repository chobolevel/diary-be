package com.diary.domain.entity.products.options

import org.springframework.stereotype.Component

@Component
class ProductOptionRepositoryWrapper(
    private val repository: ProductOptionRepository
) {

    fun save(productOption: ProductOption): ProductOption {
        return repository.save(productOption)
    }
}
