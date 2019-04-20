package ru.otus.integration.service

import org.springframework.stereotype.Component
import ru.otus.integration.dao.BookRepository
import ru.otus.integration.model.Book

@Component
class BookService(private val bookRepository: BookRepository) {

    fun findAll() = bookRepository.findAll()

    fun save(book: Book) = bookRepository.save(book)
}