package com.daangn.domain.entity.channels

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.channels.users.ChannelUser
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited

@Entity
@Table(name = "channels")
@Audited
class Channel(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Column(nullable = false, length = 100)
    var name: String
) : Audit() {

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }

    @Where(clause = "deleted = false")
    @OneToMany(mappedBy = "channel", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val channelUsers = mutableListOf<ChannelUser>()
}

enum class ChannelOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC
}

enum class ChannelUpdateMask {
    NAME
}
