package com.scrimmers.domain.entity.borad

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, String> {

    fun findByIdAndDeletedFalse(id: String): Board?
}
