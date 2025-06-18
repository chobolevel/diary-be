package com.diary.domain.entity.products.options.values

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.products.options.ProductOption
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
@Table(name = "product_option_values")
@Audited
class ProductOptionValue(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    var productOption: ProductOption? = null
    fun set(productOption: ProductOption) {
        if (this.productOption != productOption) {
            this.productOption = productOption
        }
        productOption.add(this)
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class ProductOptionValueUpdateMask {
    NAME,
    ORDER
}
