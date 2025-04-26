package com.diary.domain.entity.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.envers.Audited
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@Audited
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
class BaseEntity {

    @Column(nullable = false, updatable = false)
    @CreatedDate
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    @LastModifiedDate
    var updatedAt: OffsetDateTime? = null
}
