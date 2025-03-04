package com.daangn.api.service.users.updater

import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.domain.entity.users.regions.UserRegion
import com.daangn.domain.entity.users.regions.UserRegionUpdateMask
import org.springframework.stereotype.Component

@Component
class UserRegionUpdater {

    fun markAsUpdate(request: UpdateUserRegionRequestDto, entity: UserRegion): UserRegion {
        request.updateMask.forEach {
            when (it) {
                UserRegionUpdateMask.LATITUDE -> entity.latitude = request.latitude!!
                UserRegionUpdateMask.LONGITUDE -> entity.longitude = request.longitude!!
                UserRegionUpdateMask.REGION_1DEPTH_NAME -> entity.region1depthName = request.region1depthName!!
                UserRegionUpdateMask.REGION_2DEPTH_NAME -> entity.region2depthName = request.region2depthName!!
                UserRegionUpdateMask.REGION_3DEPTH_NAME -> entity.region3depthName = request.region3depthName!!
            }
        }
        return entity
    }
}
