package ru.otus.bookstore.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Genre

@Repository
@Transactional
interface GenreRepository: CrudRepository<Genre, Long>