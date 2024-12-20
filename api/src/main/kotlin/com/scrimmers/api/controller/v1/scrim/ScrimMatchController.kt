package com.scrimmers.api.controller.v1.scrim

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.scrim.match.CreateScrimMatchRequestDto
import com.scrimmers.api.dto.scrim.match.UpdateScrimMatchRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.scrim.ScrimMatchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Scrim Match(스크림 매치)", description = "스크림 매치 관리 API")
@RestController
@RequestMapping("/api/v1")
class ScrimMatchController(
    private val service: ScrimMatchService,
) {

    @Operation(summary = "스크림 매치 등록 API")
    @HasAuthorityUser
    @PostMapping("/scrims/matches")
    fun createScrimMatch(
        principal: Principal,
        @Valid @RequestBody
        request: CreateScrimMatchRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 매치 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/scrims/matches/{id}")
    fun updateScrimMatch(
        principal: Principal,
        @PathVariable("id") scrimMatchId: String,
        @Valid @RequestBody
        request: UpdateScrimMatchRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            scrimMatchId = scrimMatchId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "스크림 매칭 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/scrims/matches/{id}")
    fun deleteScrimMatch(
        principal: Principal,
        @PathVariable("id") scrimMatchId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            scrimMatchId = scrimMatchId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
