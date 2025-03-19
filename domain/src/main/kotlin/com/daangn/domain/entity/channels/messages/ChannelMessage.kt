package com.daangn.domain.entity.channels.messages

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
@Table(name = "channel_messages")
@Audited
class ChannelMessage(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: ChannelMessageType,
    @Column(nullable = false, length = 255)
    var content: String
) : Audit() {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    var channel: Channel? = null
    fun set(channel: Channel) {
        if (this.channel != channel) {
            this.channel = channel
        }
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User? = null
    fun set(writer: User) {
        if (this.writer != writer) {
            this.writer = writer
        }
    }

    @Column(nullable = false)
    var deleted: Boolean = false
    fun delete() {
        this.deleted = true
    }
}

enum class ChannelMessageType {
    ENTER,
    TALK,
    EXIT
}

enum class ChannelMessageOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}
