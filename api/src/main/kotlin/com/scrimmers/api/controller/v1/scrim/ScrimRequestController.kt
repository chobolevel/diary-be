package com.scrimmers.api.controller.v1.scrim

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.scrim.request.CreateScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.RejectScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.UpdateScrimRequestRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.scrim.ScrimRequestService
import com.scrimmers.api.service.scrim.query.ScrimRequestQueryCreator
import com.scrimmers.domain.entity.scrim.request.ScrimRequestOrderType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Scrim Request(스크림 요청)", description = "스크림 요청 관리 API")
@RestController
@RequestMapping("/api/v1")
class ScrimRequestController(
    private val service: ScrimRequestService,
    private val queryCreator: ScrimRequestQueryCreator
) {

    @Operation(summary = "스크림 요청 등록 API")
    @HasAuthorityUser
    @PostMapping("/scrims/requests")
    fun createScrimRequest(
        principal: Principal,
        @Valid @RequestBody
        request: CreateScrimRequestRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 목록 조회 API")
    @GetMapping("/scrims/requests")
    fun getScrimRequests(
        @RequestParam(required = false) fromTeamId: String?,
        @RequestParam(required = false) toTeamId: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<ScrimRequestOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            fromTeamId = fromTeamId,
            toTeamId = toTeamId,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount,
        )
        val result = service.getScrimRequests(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 목록 단건 조회 API")
    @GetMapping("/scrims/requests/{id}")
    fun getScrimRequest(@PathVariable("id") scrimRequestId: String): ResponseEntity<ResultResponse> {
        val result = service.getScrimRequest(scrimRequestId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 수정 API")
    @HasAuthorityUser
    @PutMapping("/scrims/requests/{id}")
    fun updateScrimRequest(
        principal: Principal,
        @PathVariable("id") scrimRequestId: String,
        @Valid @RequestBody
        request: UpdateScrimRequestRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            scrimRequestId = scrimRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 승인 API")
    @HasAuthorityUser
    @PutMapping("/scrims/requests/{id}/approve")
    fun approveScrimRequest(
        principal: Principal,
        @PathVariable("id") scrimRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.approve(
            userId = principal.getUserId(),
            scrimRequestId = scrimRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 거절 API")
    @HasAuthorityUser
    @PutMapping("/scrims/requests/{id}/reject")
    fun rejectScrimRequest(
        principal: Principal,
        @PathVariable("id") scrimRequestId: String,
        @Valid @RequestBody
        request: RejectScrimRequestRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.reject(
            userId = principal.getUserId(),
            scrimRequestId = scrimRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 요청 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/scrims/requests/{id}")
    fun deleteScrimRequest(
        principal: Principal,
        @PathVariable("id") scrimRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            scrimRequestId = scrimRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
