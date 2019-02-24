package ru.otus.bookstore.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Review

@Repository
@Transactional
interface ReviewRepository: CrudRepository<Review, Long>