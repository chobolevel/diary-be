package com.daangn.api.dto.channels

import com.daangn.domain.entity.channels.ChannelUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateChannelRequestDto(
    @field:NotEmpty
    val name: String,
    @field:Size(min = 1)
    val userIds: List<String>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateChannelRequestDto(
    val name: String?,
    @field:Size(min = 1)
    val updateMask: List<ChannelUpdateMask>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InviteChannelRequestDto(
    @field:Size(min = 1)
    val userIds: List<String>
)
