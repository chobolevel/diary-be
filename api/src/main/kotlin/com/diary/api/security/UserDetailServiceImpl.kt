package com.diary.api.security

import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val userRepositoryWrapper: UserRepositoryWrapper
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user: User = userRepositoryWrapper.findById(id = id)
        return UserDetail(user = user)
    }
}
