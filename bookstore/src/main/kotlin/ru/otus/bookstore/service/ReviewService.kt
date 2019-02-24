package ru.otus.bookstore.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.dao.BookRepository
import ru.otus.bookstore.dao.ReviewRepository
import ru.otus.bookstore.model.Review

@Service
@Transactional
class ReviewService(
    private val bookRepository: BookRepository,
    private val reviewRepository: ReviewRepository
) {

    fun addReview(bookId: Long, text: String): String {
        val book = bookRepository.findById(bookId).orElse(null)
        return if (book == null) {
            "Book with id = $bookId not found"
        } else {
            val review = reviewRepository.save(Review(text = text, book = book))
            book.reviews.add(review)
            "Review added"
        }
    }
}