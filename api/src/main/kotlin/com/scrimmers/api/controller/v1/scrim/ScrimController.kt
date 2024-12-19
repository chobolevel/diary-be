package com.scrimmers.api.controller.v1.scrim

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.scrim.CreateScrimRequestDto
import com.scrimmers.api.dto.scrim.UpdateScrimRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.scrim.ScrimService
import com.scrimmers.api.service.scrim.query.ScrimQueryCreator
import com.scrimmers.domain.entity.team.scrim.ScrimOrderType
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

@Tag(name = "Scrim(스크림)", description = "스크림 관리 API")
@RestController
@RequestMapping("/api/v1")
class ScrimController(
    private val service: ScrimService,
    private val queryCreator: ScrimQueryCreator
) {

    @Operation(summary = "스크림 등록 API")
    @HasAuthorityUser
    @PostMapping("/scrims")
    fun createScrim(
        principal: Principal,
        @Valid @RequestBody
        request: CreateScrimRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 목록 조회 API")
    @GetMapping("/scrims")
    fun getScrims(
        @RequestParam(required = false) scrimRequestId: String?,
        @RequestParam(required = false) teamId: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<ScrimOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            scrimRequestId = scrimRequestId,
            teamId = teamId,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount
        )
        val result = service.getScrims(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 단건 조회 API")
    @GetMapping("/scrims/{id}")
    fun getScrim(@PathVariable("id") scrimId: String): ResponseEntity<ResultResponse> {
        val result = service.getScrim(scrimId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/scrims/{id}")
    fun updateScrim(
        principal: Principal,
        @PathVariable("id") scrimId: String,
        @Valid @RequestBody
        request: UpdateScrimRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            scrimId = scrimId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/scrims/{id}")
    fun deleteScrim(principal: Principal, @PathVariable("id") scrimId: String): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            scrimId = scrimId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
