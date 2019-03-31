package ru.otus.crudrest.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.crudrest.model.User

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}