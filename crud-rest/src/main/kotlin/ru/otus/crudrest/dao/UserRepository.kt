package ru.otus.crudrest.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.otus.crudrest.model.User

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}