package com.scrimmers.api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

class JwtAuthorizationFilter(
    private val authManager: CustomAuthenticationManager,
    private val tokenProvider: TokenProvider
) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class, AccessDeniedException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (request.cookies.isNullOrEmpty() || request.cookies.find { it.name == "_sat" } == null) {
            chain.doFilter(request, response)
            return
        }
        val accessToken = request.cookies.find { it.name == "_sat" }!!
        tokenProvider.getAuthentication(accessToken.value).also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(request, response)
    }
}
