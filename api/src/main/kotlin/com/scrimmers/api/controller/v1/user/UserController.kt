package com.scrimmers.api.controller.v1.user

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.user.ChangePasswordRequestDto
import com.scrimmers.api.dto.user.CreateUserRequestDto
import com.scrimmers.api.dto.user.UpdateUserRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.user.UserService
import com.scrimmers.api.service.user.query.UserQueryCreator
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserOrderType
import com.scrimmers.domain.entity.user.UserRoleType
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

@Tag(name = "User(회원)", description = "회원 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserController(
    private val service: UserService,
    private val queryCreator: UserQueryCreator
) {

    @Operation(summary = "회원 등록 API")
    @PostMapping("/users")
    fun createUser(
        @Valid @RequestBody
        request: CreateUserRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(request)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 목록 조회 API")
    @GetMapping("/users")
    fun getUsers(
        @RequestParam(required = false) teamId: String?,
        @RequestParam(required = false) loginType: UserLoginType?,
        @RequestParam(required = false) role: UserRoleType?,
        @RequestParam(required = false) resigned: Boolean?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<UserOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            teamId = teamId,
            loginType = loginType,
            role = role,
            resigned = resigned
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount
        )
        val result = service.getUsers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 단건 조회 API")
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable("id") userId: String): ResponseEntity<ResultResponse> {
        val result = service.getUser(userId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 본인 정보 조회 API")
    @GetMapping("/users/me")
    @HasAuthorityUser
    fun getMe(principal: Principal): ResponseEntity<ResultResponse> {
        val result = service.getUser(principal.getUserId())
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 정보 수정 API")
    @PutMapping("/users/{id}")
    @HasAuthorityUser
    fun updateUser(
        principal: Principal,
        @PathVariable("id") userId: String,
        @Valid @RequestBody
        request: UpdateUserRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            requesterId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 비밀번호 변경 API")
    @PutMapping("/users/change-password")
    @HasAuthorityUser
    fun changePassword(
        principal: Principal,
        @Valid @RequestBody
        request: ChangePasswordRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.changePassword(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 탈퇴 API")
    @DeleteMapping("/users/{id}")
    @HasAuthorityUser
    fun resignUser(principal: Principal, @PathVariable("id") userId: String): ResponseEntity<ResultResponse> {
        val result = service.resign(
            requesterId = principal.getUserId(),
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
