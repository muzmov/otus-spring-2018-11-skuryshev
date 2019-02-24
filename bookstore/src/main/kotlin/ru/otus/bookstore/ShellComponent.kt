package ru.otus.bookstore

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.dao.AuthorRepository
import ru.otus.bookstore.dao.BookRepository
import ru.otus.bookstore.dao.GenreRepository
import ru.otus.bookstore.model.Author
import ru.otus.bookstore.model.Book
import ru.otus.bookstore.model.Genre
import ru.otus.bookstore.service.ReviewService

@ShellComponent
@Transactional
class ShellComponent(
    private val genreRepository: GenreRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val reviewService: ReviewService
) {

    @ShellMethod("List entities")
    fun list(entity: String) = when (entity) {
        "authors" -> authorRepository.findAll().toString()
        "genres" -> genreRepository.findAll().toString()
        "books" -> bookRepository.findAll().toString()
        else -> "Wrong param"
    }

    @ShellMethod("Add entity")
    fun add(
        entity: String, name: String,
        @ShellOption(defaultValue = "") description: String,
        @ShellOption(defaultValue = "") genreIds: String,
        @ShellOption(defaultValue = "") authorIds: String
    ) = when (entity) {
        "author" -> authorRepository.save(Author(name = name)).let { "id = ${it.id}" }
        "genre" -> genreRepository.save(Genre(name = name)).let { "id = ${it.id}" }
        "book" -> bookRepository.save(
            Book(
                title = name,
                description = description
            ).apply {
                genres = genreRepository.findAllById(genreIds.split(",").map { it.toLong() }).toMutableSet()
                authors = authorRepository.findAllById(authorIds.split(",").map { it.toLong() }).toMutableSet()
            }
        ).let { "id = ${it.id}" }
        else -> "Wrong param"
    }

    @ShellMethod("Update entity")
    fun update(
        entity: String, id: Long, name: String,
        @ShellOption(defaultValue = "") description: String,
        @ShellOption(defaultValue = "") genreIds: String,
        @ShellOption(defaultValue = "") authorIds: String
    ) = when (entity) {
        "author" -> authorRepository.findById(id).ifPresent { it.name = name } .let { "Updated" }
        "genre" -> genreRepository.findById(id).ifPresent { it.name = name } .let { "Updated" }
        "book" -> bookRepository.findById(id).ifPresent {
            it.title = name
            it.description = description
            it.genres = genreRepository.findAllById(genreIds.split(",").map { it.toLong() }).toMutableSet()
            it.authors = authorRepository.findAllById(authorIds.split(",").map { it.toLong() }).toMutableSet()
        } .let { "Updated" }
        else -> "Wrong param"
    }

    @ShellMethod("Delete entity")
    fun delete(entity: String, id: Long) = when (entity) {
        "author" -> authorRepository.deleteById(id).let { "Deleted" }
        "genre" -> genreRepository.deleteById(id).let { "Deleted" }
        "book" -> bookRepository.deleteById(id).let { "Deleted" }
        else -> "Wrong param"
    }

    @ShellMethod("Add review to book")
    fun review(bookId: Long, text: String) = reviewService.addReview(bookId, text)
}