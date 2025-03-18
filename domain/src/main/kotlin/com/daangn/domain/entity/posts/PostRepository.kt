package com.daangn.domain.entity.posts

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, String> {

    fun findByIdAndDeletedFalse(id: String): Post?

    fun existsByIdAndDeletedFalse(id: String): Boolean
}
