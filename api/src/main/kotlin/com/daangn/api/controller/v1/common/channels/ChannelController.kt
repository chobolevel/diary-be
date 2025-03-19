package com.daangn.api.controller.v1.common.channels

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.InviteChannelRequestDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.channels.ChannelService
import com.daangn.api.service.channels.query.ChannelQueryCreator
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.channels.ChannelOrderType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Channel(채널)", description = "채널 관리 API")
@RestController
@RequestMapping("/api/v1")
class ChannelController(
    private val service: ChannelService,
    private val queryCreator: ChannelQueryCreator
) {

    @Operation(summary = "채널 생성 API")
    @HasAuthorityUser
    @PostMapping("/channels")
    fun create(
        principal: Principal,
        @Valid @RequestBody
        request: CreateChannelRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "채널 목록 조회 API")
    @HasAuthorityUser
    @GetMapping("/channels")
    fun getChannels(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<ChannelOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            name = name
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size
        )
        val result = service.getChannels(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "채널 단건 조회 API")
    @HasAuthorityUser
    @GetMapping("/channels/{id}")
    fun getChannel(@PathVariable("id") channelId: String): ResponseEntity<ResultResponseDto> {
        val result = service.getChannel(
            channelId = channelId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "채널 초대 API")
    @HasAuthorityUser
    @PostMapping("/channels/{id}/invite")
    fun invite(
        principal: Principal,
        @PathVariable("id") channelId: String,
        @Valid @RequestBody
        request: InviteChannelRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.invite(
            userId = principal.getUserId(),
            channelId = channelId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "채널 떠나기 API")
    @HasAuthorityUser
    @PostMapping("/channels/{id}/leave")
    fun leave(principal: Principal, @PathVariable("id") channelId: String): ResponseEntity<ResultResponseDto> {
        val result = service.leave(
            userId = principal.getUserId(),
            channelId = channelId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
