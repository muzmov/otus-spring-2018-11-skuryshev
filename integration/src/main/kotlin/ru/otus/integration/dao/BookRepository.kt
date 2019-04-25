package ru.otus.integration.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.integration.model.Book

@Repository
interface BookRepository: MongoRepository<Book, String>