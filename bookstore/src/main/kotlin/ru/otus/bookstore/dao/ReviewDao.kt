package ru.otus.bookstore.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Review
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class ReviewDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun add(review: String) = Review(text = review).also { em.persist(it) }
}