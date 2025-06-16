package com.diary.domain.entity.products

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.type.ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "products")
@Audited
class Product(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: ProductStatus,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    var productCategory: ProductCategory? = null
    fun set(productCategory: ProductCategory) {
        if (this.productCategory != productCategory) {
            this.productCategory = productCategory
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class ProductStatus(val desc: String) {
    ON_SALE("판매중"),
    SOLD_OUT("품절"),
    HIDING("숨김")
}

enum class ProductOrderType {
    ORDER_ASC,
    ORDER_DESC,
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class ProductUpdateMask {
    CATEGORY,
    NAME,
    STATUS,
    ORDER
}
