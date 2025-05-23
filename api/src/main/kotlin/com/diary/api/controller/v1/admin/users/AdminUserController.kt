package com.diary.api.controller.v1.admin.users

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.UserOrderType
import com.diary.domain.entity.users.UserQueryFilter
import com.diary.domain.entity.users.UserRoleType
import com.diary.domain.entity.users.UserSignUpType
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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

@Tag(name = "User(괸라자 회원)", description = "관리자 회원 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminUserController(
    private val service: UserService
) {

    @Operation(summary = "관리자 회원 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/users")
    fun join(
        @Valid @RequestBody
        request: CreateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.join(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "관리자 회원 목록 조회 API")
    @HasAuthorityAdmin
    @GetMapping("/users")
    fun getUsers(
        @RequestParam(required = false) username: String?,
        @RequestParam(required = false) signUpType: UserSignUpType?,
        @RequestParam(required = false) nickname: String?,
        @RequestParam(required = false) role: UserRoleType?,
        @RequestParam(required = false) resigned: Boolean?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<UserOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = UserQueryFilter(
            username = username,
            nickname = nickname,
            signUpType = signUpType,
            role = role,
            resigned = resigned,
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 20
        )
        val result: PaginationResponseDto = service.getUsers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "관리자 회원 단건 조회 API")
    @HasAuthorityAdmin
    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: ID): ResponseEntity<ResultResponseDto> {
        val result: UserResponseDto = service.getUser(id = userId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "관리자 회원 정보 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/users/{userId}")
    fun update(
        @PathVariable userId: ID,
        @Valid @RequestBody
        request: UpdateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            id = userId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "괸리자 회원 비밀번호 변경 API")
    @HasAuthorityAdmin
    @PostMapping("/users/{userId}/change-password")
    fun changePassword(
        @PathVariable userId: ID,
        @Valid @RequestBody
        request: ChangeUserPasswordRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.changePassword(
            id = userId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "관리자 회원 탈퇴 처리 API")
    @HasAuthorityAdmin
    @DeleteMapping("/users/{userId}")
    fun resign(@PathVariable userId: ID): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.resign(id = userId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
