package ru.otus.crudrest.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.crudrest.dao.RoleRepository
import ru.otus.crudrest.dao.UserRepository
import ru.otus.crudrest.model.Role
import ru.otus.crudrest.model.User
import ru.otus.crudrest.model.UserDetailsImpl
import javax.annotation.PostConstruct

@Component
@Transactional
class DbUserDetailsService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository
): UserDetailsService {

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun init() {
        val userRole = roleRepository.save(Role(role = "ROLE_USER"))
        val adminRole = roleRepository.save(Role(role = "ROLE_ADMIN"))
        userRepository.save(User(username = "user1", password = BCryptPasswordEncoder().encode("password"), roles = setOf(userRole)))
        userRepository.save(User(username = "user2", password = BCryptPasswordEncoder().encode("password"), roles = setOf(userRole)))
        userRepository.save(User(username = "admin", password = BCryptPasswordEncoder().encode("admin"), roles = setOf(adminRole)))
    }

    override fun loadUserByUsername(username: String) =
        UserDetailsImpl(userRepository.findByUsername(username) ?: throw IllegalArgumentException("User not found"))
}