package com.scrimmers.api.advice

import com.scrimmers.api.dto.common.ErrorResponse
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun accessDeniedExceptionHandler(e: org.springframework.security.access.AccessDeniedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = ErrorCode.FORBIDDEN,
            errorMessage = ErrorCode.FORBIDDEN.desc
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

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

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(e: BadCredentialsException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = ErrorCode.BAD_CREDENTIALS,
            errorMessage = e.message.toString()
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(DataNotFoundException::class)
    fun dataNotFoundExceptionHandler(e: DataNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = e.errorCode,
            errorMessage = e.message
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(ParameterInvalidException::class)
    fun parameterInvalidException(e: ParameterInvalidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = e.errorCode,
            errorMessage = e.message
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }
}
