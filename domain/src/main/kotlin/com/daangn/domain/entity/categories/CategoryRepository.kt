package com.daangn.domain.entity.categories

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String> {

    fun findByIdAndDeletedFalse(id: String): Category?
}
