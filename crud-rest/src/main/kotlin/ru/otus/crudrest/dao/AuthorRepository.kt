package ru.otus.crudrest.dao

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.otus.crudrest.model.Author

@Repository
interface AuthorRepository: ReactiveMongoRepository<Author, String> {
    fun save(author: Mono<Author>): Mono<Author>
}