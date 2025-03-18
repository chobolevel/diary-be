package com.daangn.domain.entity.channels.users

import com.daangn.domain.entity.Audit
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.users.User
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
@Table(name = "channel_users")
@Audited
class ChannelUser(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var grade: ChannelUserGrade
) : Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    var channel: Channel? = null
    fun set(channel: Channel) {
        if (this.channel != channel) {
            this.channel = channel
        }
        if (!channel.channelUsers.contains(this)) {
            channel.channelUsers.add(this)
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

enum class ChannelUserGrade {
    MASTER,
    GENERAL
}
