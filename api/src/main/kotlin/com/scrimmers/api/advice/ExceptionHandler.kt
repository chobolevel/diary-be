package com.scrimmers.api.advice

import com.scrimmers.api.dto.common.ErrorResponse
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = ErrorCode.PARAMETER_INVALID,
            errorMessage = e.localizedMessage
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(PolicyException::class)
    fun policyExceptionHandler(e: PolicyException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = e.errorCode,
            errorMessage = e.message
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }
}
