package com.daangn.domain.entity.users.regions

import com.daangn.domain.entity.Audit
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
@Table(name = "user_regions")
@Audited
class UserRegion(
    @Id
    @Column(nullable = false, updatable = false, length = 13)
    var id: String,
    @Column(nullable = false)
    var latitude: Double,
    @Column(nullable = false)
    var longitude: Double,
    @Column(nullable = false, length = 100, name = "region_1depth_name")
    var region1depthName: String,
    @Column(nullable = false, length = 100, name = "region_2depth_name")
    var region2depthName: String,
    @Column(nullable = false, length = 100, name = "region_3depth_name")
    var region3depthName: String
) : Audit() {

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

enum class UserRegionOrderType {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
}

enum class UserRegionUpdateMask {
    LATITUDE,
    LONGITUDE,
    REGION_1DEPTH_NAME,
    REGION_2DEPTH_NAME,
    REGION_3DEPTH_NAME
}
