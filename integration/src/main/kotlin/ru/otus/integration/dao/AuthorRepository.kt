package ru.otus.integration.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import ru.otus.integration.model.Author

@Repository
interface AuthorRepository: MongoRepository<Author, String> {

    @Query("{ 'firstName' : ?0, 'lastName' : ?1, 'middleName' : ?2 }")
    fun findByCredentials(firstName: String, lastName: String, middleName: String): Author?
}