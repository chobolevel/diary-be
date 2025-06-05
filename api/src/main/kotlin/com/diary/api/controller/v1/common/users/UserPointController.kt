package com.diary.api.controller.v1.common.users

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.service.users.UserPointService
import com.diary.api.util.getUserId
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.points.UserPointOrderType
import com.diary.domain.entity.users.points.UserPointQueryFilter
import com.diary.domain.entity.users.points.UserPointType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User Point(회원 포인트)", description = "회원 포인트 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserPointController(
    private val service: UserPointService
) {

    @Operation(summary = "회원 포인트 이력 목록 조회 API")
    @HasAuthorityUser
    @GetMapping("/user/points")
    fun getUserPoints(
        principal: Principal,
        @RequestParam(required = false) type: UserPointType?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<UserPointOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = UserPointQueryFilter(
            userId = principal.getUserId(),
            type = type
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getUserPoints(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
