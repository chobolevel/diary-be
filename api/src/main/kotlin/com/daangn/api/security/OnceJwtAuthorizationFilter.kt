package com.daangn.api.security

import com.daangn.api.properties.JwtProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class OnceJwtAuthorizationFilter(
    private val tokenProvider: TokenProvider,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtProperties.tokenPrefix)) {
            filterChain.doFilter(request, response)
            return
        }
        val accessToken = authorizationHeader.substring(jwtProperties.tokenPrefix.length)
        try {
            tokenProvider.validateToken(accessToken)
            tokenProvider.getAuthentication(accessToken).also {
                SecurityContextHolder.getContext().authentication = it
            }
        } catch (e: Exception) {
            logger.warn("Invalid Token", e)
        }
        filterChain.doFilter(request, response)
    }
}
