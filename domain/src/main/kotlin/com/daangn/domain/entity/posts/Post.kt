package com.daangn.domain.entity.posts

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.categories.Category
import com.daangn.domain.entity.posts.image.PostImage
import com.daangn.domain.entity.posts.image.PostImageType
import com.daangn.domain.entity.users.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "posts")
@Audited
class Post(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Column(nullable = false, length = 100)
    var title: String,
    @Column(nullable = false)
    var content: String,
    @Column(nullable = false)
    var salePrice: Int,
    @Column(nullable = false)
    var freeShared: Boolean
) : Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User? = null
    fun set(writer: User) {
        if (this.writer != writer) {
            this.writer = writer
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null
    fun set(category: Category) {
        if (this.category != category) {
            this.category = category
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }

    @Where(clause = "deleted = false")
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images = mutableListOf<PostImage>()
    fun getTypeImages(type: PostImageType?): List<PostImage> {
        return when (type) {
            PostImageType.MAIN -> this.images.filter { it.type == PostImageType.MAIN }
            else -> this.images
        }
    }
    fun deleteImages(type: PostImageType?) {
        val filteredImages = when (type) {
            PostImageType.MAIN -> this.images.filter { it.type == PostImageType.MAIN }
            else -> this.images
        }
        filteredImages.forEach { it.delete() }
    }
}

enum class PostOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}

enum class PostUpdateMask {
    CATEGORY,
    TITLE,
    CONTENT,
    SALE_PRICE,
    FREE_SHARED,
    MAIN_IMAGES
}
