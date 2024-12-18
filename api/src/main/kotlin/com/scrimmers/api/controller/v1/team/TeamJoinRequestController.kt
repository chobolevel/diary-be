package com.scrimmers.api.controller.v1.team

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.team.join.CreateTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.RejectTeamJoinRequestDto
import com.scrimmers.api.dto.team.join.UpdateTeamJoinRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.team.TeamJoinRequestService
import com.scrimmers.api.service.team.query.TeamJoinRequestQueryCreator
import com.scrimmers.domain.entity.team.join.TeamJoinRequestOrderType
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

@Tag(name = "Team Join Request(팀 합류 요청)", description = "팀 합류 요청 관리 API")
@RestController
@RequestMapping("/api/v1")
class TeamJoinRequestController(
    private val service: TeamJoinRequestService,
    private val queryCreator: TeamJoinRequestQueryCreator
) {

    @Operation(summary = "팀 합류 요청 등록 API")
    @HasAuthorityUser
    @PostMapping("/teams/join-requests")
    fun createTeamJoinRequests(
        principal: Principal,
        @Valid @RequestBody
        request: CreateTeamJoinRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 목록 조회 API")
    @GetMapping("/teams/join-requests")
    fun getTeamJoinRequests(
        @RequestParam(required = false) teamId: String?,
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<TeamJoinRequestOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            teamId = teamId,
            userId = userId,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount
        )
        val result = service.getTeamJoinRequests(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 단건 조회 API")
    @GetMapping("/teams/join-requests/{id}")
    fun getTeamJoinRequest(@PathVariable("id") teamJoinRequestId: String): ResponseEntity<ResultResponse> {
        val result = service.getTeamJoinRequest(teamJoinRequestId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 수정 API")
    @HasAuthorityUser
    @PutMapping("/teams/join-requests/{id}")
    fun updateTeamJoinRequests(
        principal: Principal,
        @PathVariable("id") teamJoinRequestId: String,
        @Valid @RequestBody
        request: UpdateTeamJoinRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            teamJoinRequestId = teamJoinRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 승인 API")
    @HasAuthorityUser
    @PutMapping("/teams/join-requests/{id}/approve")
    fun approveTeamJoinRequests(
        principal: Principal,
        @PathVariable("id") teamJoinRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.approve(
            userId = principal.getUserId(),
            teamJoinRequestId = teamJoinRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 거절 API")
    @HasAuthorityUser
    @PutMapping("/teams/join-requests/{id}/reject")
    fun rejectTeamJoinRequests(
        principal: Principal,
        @PathVariable("id") teamJoinRequestId: String,
        @Valid @RequestBody
        request: RejectTeamJoinRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.reject(
            userId = principal.getUserId(),
            teamJoinRequestId = teamJoinRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 합류 요청 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/teams/join-requests/{id}")
    fun deleteTeamJoinRequests(
        principal: Principal,
        @PathVariable("id") teamJoinRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            teamJoinRequestId = teamJoinRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
