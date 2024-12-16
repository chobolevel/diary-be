package com.scrimmers.api.security

import com.scrimmers.domain.entity.user.UserFinder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userFinder: UserFinder
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user = userFinder.findById(id)
        return CustomUserDetails(user)
    }
}
