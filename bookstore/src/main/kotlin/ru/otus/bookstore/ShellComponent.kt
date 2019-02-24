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
    private val bookRepository: BookRepository,
    private val genreRepository: GenreRepository,
    private val authorRepository: AuthorRepository,
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
        entity: String, var1: String,
        @ShellOption(defaultValue = "") var2: String,
        @ShellOption(defaultValue = "") var3: String,
        @ShellOption(defaultValue = "") var4: String
    ) = when (entity) {
        "author" -> authorRepository.save(Author(firstName = var1, lastName = var2, middleName = var3)).let { "id = ${it.id}" }
        "genre" -> genreRepository.save(Genre(name = var1, description = var2)).let { "id = ${it.id}" }
        "book" -> bookRepository.save(
            Book(
                title = var1,
                description = var2
            ).apply {
                genres = genreRepository.findAllById(var3.split(",")).toMutableSet()
                authors = authorRepository.findAllById(var4.split(",")).toMutableSet()
            }
        ).let { "id = ${it.id}" }
        else -> "Wrong param"
    }

    @ShellMethod("Update entity")
    fun update(
        entity: String, id: String, var1: String,
        @ShellOption(defaultValue = "") var2: String,
        @ShellOption(defaultValue = "") var3: String,
        @ShellOption(defaultValue = "") var4: String
    ) = when (entity) {
        "author" -> authorRepository.findById(id).ifPresent {
            it.firstName = var1
            it.lastName = var2
            it.middleName = var3
            authorRepository.save(it)
        } .let { "Updated" }
        "genre" -> genreRepository.findById(id).ifPresent {
            it.name = var1
            it.description = var2
            genreRepository.save(it)
        } .let { "Updated" }
        "book" -> bookRepository.findById(id).ifPresent {
            it.title = var1
            it.description = var2
            it.genres = genreRepository.findAllById(var3.split(",")).toMutableSet()
            it.authors = authorRepository.findAllById(var4.split(",")).toMutableSet()
            bookRepository.save(it)
        } .let { "Updated" }
        else -> "Wrong param"
    }

    @ShellMethod("Delete entity")
    fun delete(entity: String, id: String) = when (entity) {
        "author" -> authorRepository.deleteById(id).let { "Deleted" }
        "genre" -> genreRepository.deleteById(id).let { "Deleted" }
        "book" -> bookRepository.deleteById(id).let { "Deleted" }
        else -> "Wrong param"
    }

    @ShellMethod("Add review to book")
    fun review(bookId: String, text: String) = reviewService.addReview(bookId, text)
}