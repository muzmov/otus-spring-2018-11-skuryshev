package ru.otus.crudrest.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import ru.otus.crudrest.dao.UserRepository
import ru.otus.crudrest.model.User
import ru.otus.crudrest.model.UserDetailsImpl
import javax.annotation.PostConstruct

@Component
class MongoUserDetailsService(val userRepository: UserRepository): UserDetailsService {

    @PostConstruct
    fun init() {
        userRepository.deleteAll()
        userRepository.save(User(username = "user", password = BCryptPasswordEncoder().encode("password")))
        userRepository.save(User(username = "admin", password = BCryptPasswordEncoder().encode("admin")))
    }

    override fun loadUserByUsername(username: String) =
        UserDetailsImpl(userRepository.findByUsername(username) ?: throw IllegalArgumentException("User not found"))
}