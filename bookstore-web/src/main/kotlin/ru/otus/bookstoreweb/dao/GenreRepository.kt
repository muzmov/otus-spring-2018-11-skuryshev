package ru.otus.bookstoreweb.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.bookstoreweb.model.Genre

@Repository
interface GenreRepository: MongoRepository<Genre, String>