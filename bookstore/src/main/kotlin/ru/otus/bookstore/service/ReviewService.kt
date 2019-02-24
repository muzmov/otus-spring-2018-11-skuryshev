package ru.otus.bookstore.service

import org.springframework.stereotype.Service
import ru.otus.bookstore.dao.BookRepository
import ru.otus.bookstore.model.Review

@Service
class ReviewService(
    private val bookRepository: BookRepository
) {

    fun addReview(bookId: String, text: String): String {
        val book = bookRepository.findById(bookId).orElse(null)
        return if (book == null) {
            "Book with id = $bookId not found"
        } else {
            book.reviews.add(Review(text = text))
            bookRepository.save(book)
            "Review added"
        }
    }
}