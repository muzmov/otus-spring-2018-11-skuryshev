package ru.otus.bookstoreweb.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.bookstoreweb.dao.GenreRepository
import ru.otus.bookstoreweb.model.Book
import ru.otus.bookstoreweb.model.Genre
import java.util.*

@Service
class GenreService(private val genreRepository: GenreRepository) {

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackDelete")
    fun deleteById(id: String) = genreRepository.deleteById(id)

    @HystrixCommand(commandKey = "dbUpdate", fallbackMethod = "fallbackSave")
    fun findById(id: String) = genreRepository.findById(id)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindById")
    fun save(genre: Genre) = genreRepository.save(genre)

    @HystrixCommand(commandKey = "dbRead", fallbackMethod = "fallbackFindAll")
    fun findAll() = genreRepository.findAll()

    fun fallbackDelete(id: String) {}

    fun fallbackSave(genre: Genre) = null

    fun fallbackFindById(id: String) = Optional.empty<Genre>()

    fun fallbackFindAll() = listOf<Genre>()
}