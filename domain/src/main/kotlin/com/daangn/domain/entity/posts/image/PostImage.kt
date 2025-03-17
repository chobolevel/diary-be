package com.daangn.domain.entity.posts.image

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.posts.Post
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
@Table(name = "post_images")
@Audited
class PostImage(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: PostImageType,
    @Column(nullable = false, length = 255)
    var url: String,
    @Column(nullable = false, length = 100)
    var name: String,
    @Column(nullable = false)
    var order: Int
) : Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
    fun set(post: Post) {
        if (this.post != post) {
            this.post = post
        }
        if (!post.images.contains(this)) {
            post.images.add(this)
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class PostImageType {
    MAIN
}
