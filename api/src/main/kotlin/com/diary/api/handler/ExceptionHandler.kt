package com.diary.api.handler

import com.diary.api.dto.common.ErrorResponseDto
import com.diary.api.util.toSnakeCase
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import com.diary.domain.exception.PolicyException
import com.diary.domain.exception.UnauthorizedException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(PolicyException::class)
    fun policyExceptionHandler(e: PolicyException): ResponseEntity<ErrorResponseDto> {
        val errorCode = e.errorCode
        val errorMessage = e.message
        val errorResponse = ErrorResponseDto(
            errorCode = errorCode,
            errorMessage = errorMessage
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(DataNotFoundException::class)
    fun dataNotFoundExceptionHandler(e: DataNotFoundException): ResponseEntity<ErrorResponseDto> {
        val errorCode = e.errorCode
        val errorMessage = e.message
        val errorResponse = ErrorResponseDto(
            errorCode = errorCode,
            errorMessage = errorMessage
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(InvalidParameterException::class)
    fun parameterInvalidExceptionHandler(e: InvalidParameterException): ResponseEntity<ErrorResponseDto> {
        val errorCode = e.errorCode
        val errorMessage = e.message
        val errorResponse = ErrorResponseDto(
            errorCode = errorCode,
            errorMessage = errorMessage
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedExceptionHandler(e: UnauthorizedException): ResponseEntity<ErrorResponseDto> {
        val errorCode = e.errorCode
        val errorMessage = e.message
        val errorResponse = ErrorResponseDto(
            errorCode = errorCode,
            errorMessage = errorMessage
        )
        return ResponseEntity.status(e.status).body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = when (e.cause) {
            is MissingKotlinParameterException -> {
                val cause = e.cause as MissingKotlinParameterException
                ErrorResponseDto(
                    errorCode = ErrorCode.INVALID_PARAMETER,
                    errorMessage = "[${cause.parameter.name.toString().toSnakeCase()}]은(는) 필수 값입니다."
                )
            }
            is InvalidFormatException -> {
                val cause = e.cause as InvalidFormatException
                ErrorResponseDto(
                    errorCode = ErrorCode.INVALID_PARAMETER,
                    errorMessage = "[${cause.path[0].fieldName}]의 값이 올바르지 않습니다."
                )
            }
            else -> {
                ErrorResponseDto(
                    errorCode = ErrorCode.INVALID_REQUEST_BODY,
                    errorMessage = ErrorCode.INVALID_REQUEST_BODY.message
                )
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(
            errorCode = ErrorCode.INVALID_PARAMETER,
            errorMessage = e.bindingResult.allErrors.firstOrNull()?.defaultMessage ?: ErrorCode.INVALID_REQUEST_BODY.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(e: BadCredentialsException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(
            errorCode = ErrorCode.BAD_CREDENTIALS,
            errorMessage = e.message ?: ErrorCode.BAD_CREDENTIALS.message
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(e: AccessDeniedException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(
            errorCode = ErrorCode.ACCESS_DENIED,
            errorMessage = ErrorCode.ACCESS_DENIED.message
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupportExceptionHandler(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(
            errorCode = ErrorCode.METHOD_NOT_ALLOWED,
            errorMessage = ErrorCode.METHOD_NOT_ALLOWED.message
        )
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun unknownExceptionHandler(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponseDto> {
        val errorCode = ErrorCode.UNKNOWN
        val errorResponse = ErrorResponseDto(
            errorCode = errorCode,
            errorMessage = errorCode.message
        )
        logger.error("[(${request.method}) ${request.requestURL} ] Internal server error: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
