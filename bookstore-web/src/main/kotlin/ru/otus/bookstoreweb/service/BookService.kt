package ru.otus.bookstoreweb.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.bookstoreweb.dao.BookRepository
import ru.otus.bookstoreweb.model.Book
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository) {

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackDelete")
    fun deleteById(id: String) = bookRepository.deleteById(id)

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackSave")
    fun save(book: Book) = bookRepository.save(book)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindById")
    fun findById(id: String) = bookRepository.findById(id)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindAll")
    fun findAll() = bookRepository.findAll()

    fun fallbackDelete(id: String) {}

    fun fallbackSave(book: Book) = null

    fun fallbackFindById(id: String) = Optional.empty<Book>()

    fun fallbackFindAll() = listOf<Book>()
}