package com.daangn.api.security

import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(
    private val userRepositoryWrapper: UserRepositoryWrapper
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user = userRepositoryWrapper.findById(id)
        return UserDetailsImpl(user)
    }
}
