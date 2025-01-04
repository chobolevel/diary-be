package com.scrimmers.domain.entity.borad.category

import com.scrimmers.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "board_categories")
@Audited
class BoardCategory(
    @Id
    @Column(nullable = false, updatable = false)
    var id: String,
    @Column(nullable = false)
    var code: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @Column(nullable = false)
    var deleted: Boolean = false

    fun delete() {
        this.deleted = true
    }
}

enum class BoardCategoryOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
    ORDER_ASC,
    ORDER_DESC
}

enum class BoardCategoryUpdateMask {
    CODE,
    NAME,
    ORDER
}
