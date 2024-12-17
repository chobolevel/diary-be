package com.scrimmers.api.security

import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserLoginType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(user.role.name)
    }

    override fun getPassword(): String {
        return when (user.loginType) {
            UserLoginType.GENERAL -> user.password!!
            else -> user.socialId!!
        }
    }

    override fun getUsername(): String {
        return user.id
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
