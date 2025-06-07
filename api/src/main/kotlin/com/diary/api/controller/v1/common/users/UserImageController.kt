package com.diary.api.controller.v1.common.users

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.service.users.UserImageService
import com.diary.api.util.getUserId
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User Image(회원 이미지)", description = "회원 이미지 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserImageController(
    private val service: UserImageService
) {

    @Operation(summary = "회원 이미지 등록 API")
    @HasAuthorityUser
    @PostMapping("/user/images")
    fun create(
        principal: Principal,
        @Valid @RequestBody
        request: CreateUserImageRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "회원 이미지 수정 API")
    @HasAuthorityUser
    @PutMapping("/user/images/{id}")
    fun update(
        principal: Principal,
        @PathVariable("id") userImageId: ID,
        @Valid @RequestBody
        request: UpdateUserImageRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            userId = principal.getUserId(),
            userImageId = userImageId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 이미지 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/user/images/{id}")
    fun delete(
        principal: Principal,
        @PathVariable("id") userImageId: ID
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(
            userId = principal.getUserId(),
            userImageId = userImageId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
