package com.scrimmers.api.controller.v1.user

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.dto.user.image.CreateUserImageRequestDto
import com.scrimmers.api.dto.user.image.UpdateUserImageRequestDto
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.user.UserImageService
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

@Tag(name = "UserImage(회원 이미지)", description = "회원 이미지 관리 API")
@RestController
@RequestMapping("/api/v1/users")
class UserImageController(
    private val service: UserImageService
) {

    @Operation(summary = "회원 이미지 등록 API")
    @HasAuthorityUser
    @PostMapping("/images")
    fun createUserImage(
        principal: Principal,
        @Valid @RequestBody
        request: CreateUserImageRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 이미지 수정 API")
    @HasAuthorityUser
    @PutMapping("/images/{id}")
    fun updateUserImage(
        principal: Principal,
        @PathVariable("id") userImageId: String,
        @Valid @RequestBody
        request: UpdateUserImageRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            userImageId = userImageId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "회원 이미지 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/images/{id}")
    fun deleteUserImage(
        principal: Principal,
        @PathVariable("id") userImageId: String
    ): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            userImageId = userImageId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
