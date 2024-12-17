package com.scrimmers.api.controller.v1.team

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.team.CreateTeamRequestDto
import com.scrimmers.api.dto.team.UpdateTeamRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.team.TeamService
import com.scrimmers.api.service.team.query.TeamQueryCreator
import com.scrimmers.domain.entity.team.TeamOrderType
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

@Tag(name = "Team(팀)", description = "팀 관리 API")
@RestController
@RequestMapping("/api/v1")
class TeamController(
    private val service: TeamService,
    private val queryCreator: TeamQueryCreator
) {

    @Operation(summary = "팀 등록 API")
    @HasAuthorityUser
    @PostMapping("/teams")
    fun createTeam(
        principal: Principal,
        @Valid @RequestBody
        request: CreateTeamRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 목록 조회 API")
    @GetMapping("/teams")
    fun getTeams(
        @RequestParam(required = false) ownerId: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<TeamOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            ownerId = ownerId,
            name = name,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount,
        )
        val result = service.getTeams(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 단건 조회 API")
    @GetMapping("/teams/{id}")
    fun getTeam(@PathVariable("id") teamId: String): ResponseEntity<ResultResponse> {
        val result = service.getTeam(teamId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/teams/{id}")
    fun updateTeam(
        principal: Principal,
        @PathVariable("id") teamId: String,
        @Valid @RequestBody
        request: UpdateTeamRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            teamId = teamId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/teams/{id}")
    fun deleteTeam(principal: Principal, @PathVariable("id") teamId: String): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            teamId = teamId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
