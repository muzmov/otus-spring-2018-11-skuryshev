package ru.otus.crudrest.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val user: User): UserDetails {
    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority("user"))

    override fun isEnabled() = true

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}