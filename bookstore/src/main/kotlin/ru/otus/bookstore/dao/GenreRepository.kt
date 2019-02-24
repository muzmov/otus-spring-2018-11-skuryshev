package ru.otus.bookstore.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Genre

@Repository
interface GenreRepository: MongoRepository<Genre, String>