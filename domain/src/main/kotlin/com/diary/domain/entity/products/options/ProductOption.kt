package com.diary.domain.entity.products.options

import com.diary.domain.entity.common.BaseEntity
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.options.values.ProductOptionValue
import com.diary.domain.type.ID
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "product_options")
@Audited
class ProductOption(
    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 13)
    val id: ID,
    @Column(nullable = false, length = 40)
    var name: String,
    @Column(nullable = false)
    var order: Int
) : BaseEntity() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null
    fun set(product: Product) {
        if (this.product != product) {
            this.product = product
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }

    @OneToMany(mappedBy = "productOption", cascade = [CascadeType.ALL], orphanRemoval = true)
    val productOptionValues: MutableList<ProductOptionValue> = mutableListOf()
    fun add(productOptionValue: ProductOptionValue) {
        if (!this.productOptionValues.contains(productOptionValue)) {
            this.productOptionValues.add(productOptionValue)
        }
    }
}

enum class ProductOptionUpdateMask {
    NAME,
    ORDER
}
