package com.daangn.domain.entity.categories

import com.daangn.domain.entity.Audit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "categories")
@Audited
class Category(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Column(nullable = false, length = 255)
    var iconUrl: String,
    @Column(nullable = false, length = 100)
    var name: String,
    @Column(nullable = false)
    var order: Int
) : Audit() {

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class CategoryOrderType {
    ORDER_ASC,
    ORDER_DESC,
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class CategoryUpdateMask {
    ICON_URL,
    NAME,
    ORDER
}
