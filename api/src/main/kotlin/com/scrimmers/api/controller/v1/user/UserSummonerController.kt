package com.scrimmers.api.controller.v1.user

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.user.summoner.CreateUserSummonerRequestDto
import com.scrimmers.api.dto.user.summoner.UpdateUserSummonerRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.user.UserSummonerService
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

@Tag(name = "User Summoner(회원 소환사 정보)", description = "회원 소환사 정보 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserSummonerController(
    private val service: UserSummonerService
) {

    @Operation(summary = "회원 소환사 정보 등록 API")
    @HasAuthorityUser
    @PostMapping("/users/summoners")
    fun createUserSummoner(
        principal: Principal,
        @Valid @RequestBody
        request: CreateUserSummonerRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 소환사 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/users/summoners/{id}")
    fun updateUserSummoner(
        principal: Principal,
        @PathVariable("id") userSummonerId: String,
        @Valid @RequestBody
        request: UpdateUserSummonerRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            userSummonerId = userSummonerId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 소환사 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/users/summoners/{id}")
    fun deleteUserSummnoner(
        principal: Principal,
        @PathVariable("id") userSummonerId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            userSummonerId = userSummonerId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
