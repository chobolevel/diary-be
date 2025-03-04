package com.daangn.api.controller.v1.admin.users

import com.daangn.api.annotation.HasAuthorityAdmin
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.users.UserService
import com.daangn.api.service.users.query.UserQueryCreator
import com.daangn.domain.entity.users.UserOrderType
import com.daangn.domain.entity.users.UserRoleType
import com.daangn.domain.entity.users.UserSignUpType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User(회원)", description = "관리자 회원 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminUserController(
    private val service: UserService,
    private val queryCreator: UserQueryCreator
) {

    @Operation(summary = "관리자 회원 목록 조회 API")
    @HasAuthorityAdmin
    @GetMapping("/users")
    fun getUsers(
        @RequestParam(required = false) signUpType: UserSignUpType?,
        @RequestParam(required = false) role: UserRoleType?,
        @RequestParam(required = false) resigned: Boolean?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<UserOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            signUpType = signUpType,
            role = role,
            resigned = resigned,
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size
        )
        val result = service.getUsers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "관리자 회원 단건 조회 API")
    @HasAuthorityAdmin
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable("id") userId: String): ResponseEntity<ResultResponseDto> {
        val result = service.getUser(
            userId = userId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
