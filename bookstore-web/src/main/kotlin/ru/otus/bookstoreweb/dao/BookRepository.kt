package ru.otus.bookstoreweb.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.bookstoreweb.model.Book

@Repository
interface BookRepository: MongoRepository<Book, String>