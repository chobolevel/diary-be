package com.scrimmers.api.controller.v1.team

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.team.image.CreateTeamImageRequestDto
import com.scrimmers.api.dto.team.image.UpdateTeamImageRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.team.TeamImageService
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

@Tag(name = "Team Image(팀 이미지)", description = "팀 이미지 관리 API")
@RestController
@RequestMapping("/api/v1")
class TeamImageController(
    private val service: TeamImageService
) {

    @Operation(summary = "팀 이미지 등록 API")
    @HasAuthorityUser
    @PostMapping("/teams/{teamId}/images")
    fun createTeamImage(
        principal: Principal,
        @PathVariable teamId: String,
        @Valid @RequestBody
        request: CreateTeamImageRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            teamId = teamId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 이미지 수정 API")
    @HasAuthorityUser
    @PutMapping("/teams/{teamId}/images/{teamImageId}")
    fun updateTeamImage(
        principal: Principal,
        @PathVariable teamId: String,
        @PathVariable teamImageId: String,
        @Valid @RequestBody
        request: UpdateTeamImageRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            teamId = teamId,
            teamImageId = teamImageId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "팀 이미지 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/teams/{teamId}/images/{teamImageId}")
    fun deleteTeamImage(
        principal: Principal,
        @PathVariable teamId: String,
        @PathVariable teamImageId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            teamId = teamId,
            teamImageId = teamImageId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
