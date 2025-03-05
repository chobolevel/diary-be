package com.daangn.api.controller.v1.common.users

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.service.users.UserRegionService
import com.daangn.api.service.users.query.UserRegionQueryCreator
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.users.regions.UserRegionOrderType
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
import java.security.Principal

@Tag(name = "User Region(회원 지역)", description = "회원 지역 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserRegionController(
    private val service: UserRegionService,
    private val queryCreator: UserRegionQueryCreator
) {

    @Operation(summary = "회원 지역 등록 API")
    @HasAuthorityUser
    @PostMapping("/user/regions")
    fun create(
        principal: Principal,
        @Valid @RequestBody
        request: CreateUserRegionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "회원 지역 목록 조회 API")
    @HasAuthorityUser
    @GetMapping("/user/regions")
    fun getUserRegions(
        principal: Principal,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<UserRegionOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            userId = principal.getUserId(),
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size,
        )
        val result = service.getUserRegions(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 지역 단건 조회 API")
    @HasAuthorityUser
    @GetMapping("/user/regions/{id}")
    fun getUserRegion(
        principal: Principal,
        @PathVariable("id") userRegionId: String
    ): ResponseEntity<ResultResponseDto> {
        val result = service.getUserRegion(
            userRegionId = userRegionId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 지역 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/user/regions/{id}")
    fun update(
        principal: Principal,
        @PathVariable("id") userRegionId: String,
        @Valid @RequestBody
        request: UpdateUserRegionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.update(
            userId = principal.getUserId(),
            userRegionId = userRegionId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 지역 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/user/regions/{id}")
    fun delete(principal: Principal, @PathVariable("id") userRegionId: String): ResponseEntity<ResultResponseDto> {
        val result = service.delete(
            userId = principal.getUserId(),
            userRegionId = userRegionId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
