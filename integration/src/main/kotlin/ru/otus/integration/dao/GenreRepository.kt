package ru.otus.integration.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.integration.model.Genre

@Repository
interface GenreRepository: MongoRepository<Genre, String> {
    fun findByName(name: String): Genre?
}
