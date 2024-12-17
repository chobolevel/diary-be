package com.scrimmers.api.security

import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserRepository
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class CustomAuthenticationManager(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationManager {

    @Throws(BadCredentialsException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val combine = authentication.name.split("/")
        val email = combine[0]
        val loginType = UserLoginType.find(combine[1]) ?: throw ParameterInvalidException(
            errorCode = ErrorCode.PARAMETER_INVALID,
            status = HttpStatus.BAD_REQUEST,
            message = "로그인 유형이 올바르지 않습니다.(GENERAL, KAKAO, NAVER, GOOGLE)"
        )
        val credentials = authentication.credentials.toString()
        val user = userRepository.findByEmailAndLoginTypeAndResignedFalse(
            email = email,
            loginType = loginType
        ) ?: throw BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.")
        when (loginType) {
            UserLoginType.GENERAL -> {
                if (!passwordEncoder.matches(credentials, user.password)) {
                    throw BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.")
                }
            }

            else -> {
                if (!credentials.equals(user.socialId)) {
                    throw BadCredentialsException("소셜 로그인 정보가 올바르지 않습니다.")
                }
            }
        }
        return UsernamePasswordAuthenticationToken(
            user.id,
            user.password,
            AuthorityUtils.createAuthorityList(user.role.name)
        )
    }
}
