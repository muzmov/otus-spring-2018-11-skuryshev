package ru.otus.bookstore.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Book

@Repository
interface BookRepository: MongoRepository<Book, String>