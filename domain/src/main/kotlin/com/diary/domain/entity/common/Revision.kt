package com.diary.domain.entity.common

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp

@Entity
@RevisionEntity
@Table(name = "revisions")
class Revision {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    var revisionId: Long? = null

    @RevisionTimestamp
    @Column(nullable = false, updatable = false)
    var revisionTimestamp: Long? = null
}
