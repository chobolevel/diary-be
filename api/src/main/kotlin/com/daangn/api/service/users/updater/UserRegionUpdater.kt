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
                UserRegionUpdateMask.FIRST_DEPTH_NAME -> entity.region1depthName = request.firstDepthName!!
                UserRegionUpdateMask.SECOND_DEPTH_NAME -> entity.region2depthName = request.secondDepthName!!
                UserRegionUpdateMask.THIRD_DEPTH_NAME -> entity.region3depthName = request.thirdDepthName!!
            }
        }
        return entity
    }
}
