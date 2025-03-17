package com.daangn.domain.entity.posts.likes

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.users.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "post_likes")
@Audited
class PostLike(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
) : Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
    fun set(post: Post) {
        if (this.post != post) {
            this.post = post
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    fun set(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class PostLikeOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}
