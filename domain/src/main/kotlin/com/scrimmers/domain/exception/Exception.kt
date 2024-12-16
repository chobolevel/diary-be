package com.scrimmers.domain.exception

import org.springframework.http.HttpStatus

open class ApiException(
    open val errorCode: ErrorCode,
    open val status: HttpStatus,
    override val message: String,
    open val throwable: Throwable? = null
) : RuntimeException(message, throwable)

open class ApiWarmException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)

open class ApiErrorException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)

data class PolicyException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)

data class DataNotFoundException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus = HttpStatus.NOT_FOUND,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)

data class ParameterInvalidException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)

data class UnauthorizedException(
    override val errorCode: ErrorCode,
    override val status: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val message: String,
    override val throwable: Throwable? = null
) : ApiException(errorCode, status, message, throwable)
