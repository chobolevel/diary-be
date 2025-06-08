package com.diary.domain.entity.products.categories

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "product_categories")
@Audited
class ProductCategory(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(length = 255)
    var imageUrl: String? = null,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: ProductCategory? = null
    fun set(parent: ProductCategory) {
        if (this.parent != parent) {
            this.parent = parent
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class ProductCategoryUpdateMask {
    PARENT,
    NAME,
    IMAGE_URL,
    ORDER
}

enum class ProductCategoryOrderType {
    ORDER_ASC,
    ORDER_DESC,
    CREATED_AT_ASC,
    CREATED_AT_DESC
}
