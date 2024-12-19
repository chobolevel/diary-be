package com.scrimmers.api.controller.v1.team

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.team.leave.CreateTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.RejectTeamLeaveRequestDto
import com.scrimmers.api.dto.team.leave.UpdateTeamLeaveRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.team.TeamLeaveRequestService
import com.scrimmers.api.service.team.query.TeamLeaveRequestQueryCreator
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestOrderType
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

@Tag(name = "Team Leave Request(팀 탈퇴 요청)", description = "팀 탈퇴 요청 관리 API")
@RestController
@RequestMapping("/api/v1")
class TeamLeaveRequestController(
    private val service: TeamLeaveRequestService,
    private val queryCreator: TeamLeaveRequestQueryCreator
) {

    @Operation(summary = "팀 탈퇴 요청 등록 API")
    @HasAuthorityUser
    @PostMapping("/teams/leave-requests")
    fun createTeamLeaveRequest(
        principal: Principal,
        @Valid @RequestBody
        request: CreateTeamLeaveRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 목록 조회 API")
    @GetMapping("/teams/leave-requests")
    fun getTeamLeaveRequests(
        @RequestParam(required = false) teamId: String?,
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<TeamLeaveRequestOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            teamId = teamId,
            userId = userId
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount,
        )
        val result = service.getTeamLeaveRequests(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 단건 조회 API")
    @GetMapping("/teams/leave-requests/{id}")
    fun getTeamLeaveRequest(@PathVariable("id") teamLeaveRequestId: String): ResponseEntity<ResultResponse> {
        val result = service.getTeamLeaveRequest(teamLeaveRequestId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 수정 API")
    @HasAuthorityUser
    @PutMapping("/teams/leave-requests/{id}")
    fun updateTeamLeaveRequest(
        principal: Principal,
        @PathVariable("id") teamLeaveRequestId: String,
        @Valid @RequestBody
        request: UpdateTeamLeaveRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            teamLeaveRequestId = teamLeaveRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 승인 API")
    @HasAuthorityUser
    @PutMapping("/teams/leave-requests/{id}/approve")
    fun approveTeamLeaveRequest(
        principal: Principal,
        @PathVariable("id") teamLeaveRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.approve(
            userId = principal.getUserId(),
            teamLeaveRequestId = teamLeaveRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 거절 API")
    @HasAuthorityUser
    @PutMapping("/teams/leave-requests/{id}/reject")
    fun rejectTeamLeaveRequest(
        principal: Principal,
        @PathVariable("id") teamLeaveRequestId: String,
        @Valid @RequestBody
        request: RejectTeamLeaveRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.reject(
            userId = principal.getUserId(),
            teamLeaveRequestId = teamLeaveRequestId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 탈퇴 요청 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/teams/leave-requests/{id}")
    fun deleteTeamLeaveRequest(
        principal: Principal,
        @PathVariable("id") teamLeaveRequestId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            teamLeaveRequestId = teamLeaveRequestId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
