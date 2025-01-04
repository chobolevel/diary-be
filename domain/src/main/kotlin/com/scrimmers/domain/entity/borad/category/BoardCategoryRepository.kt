package com.scrimmers.domain.entity.borad.category

import org.springframework.data.jpa.repository.JpaRepository

interface BoardCategoryRepository : JpaRepository<BoardCategory, String> {

    fun findByIdAndDeletedFalse(id: String): BoardCategory?
}
