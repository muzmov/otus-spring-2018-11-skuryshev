package ru.otus.bookstoreweb.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.bookstoreweb.dao.AuthorRepository
import ru.otus.bookstoreweb.model.Author
import java.util.*

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackDelete")
    fun deleteById(id: String) = authorRepository.deleteById(id)

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackSave")
    fun save(author: Author) = authorRepository.save(author)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindById")
    fun findById(id: String) = authorRepository.findById(id)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindAll")
    fun findAll() = authorRepository.findAll()

    fun fallbackDelete(id: String) {}

    fun fallbackSave(author: Author) = null

    fun fallbackFindById(id: String) = Optional.empty<Author>()

    fun fallbackFindAll() = listOf<Author>()
}