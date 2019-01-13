package ru.otus.bookstore.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.dao.BookDao
import ru.otus.bookstore.dao.ReviewDao

@Service
@Transactional
class ReviewService(
    private val bookDao: BookDao,
    private val reviewDao: ReviewDao
) {

    fun addReview(bookId: Long, text: String): String {
        val book = bookDao.getById(bookId)
        return if (book == null) {
            "Book with id = $bookId not found"
        } else {
            val review = reviewDao.add(text)
            review.book = book
            book.reviews.add(review)
            "Review added"
        }
    }
}