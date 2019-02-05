package ru.otus.bookstore

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import ru.otus.bookstore.dao.AuthorDao
import ru.otus.bookstore.dao.BookDao
import ru.otus.bookstore.dao.GenreDao
import ru.otus.bookstore.service.ReviewService

@ShellComponent
class ShellComponent(
    private val genreDao: GenreDao,
    private val authorDao: AuthorDao,
    private val bookDao: BookDao,
    private val reviewService: ReviewService
) {

    @ShellMethod("List entities")
    fun list(entity: String) = when (entity) {
        "authors" -> authorDao.getAll().toString()
        "genres" -> genreDao.getAll().toString()
        "books" -> bookDao.getAll().toString()
        else -> "Wrong param"
    }

    @ShellMethod("Add entity")
    fun add(
        entity: String, name: String,
        @ShellOption(defaultValue = "") description: String,
        @ShellOption(defaultValue = "") genreIds: String,
        @ShellOption(defaultValue = "") authorIds: String
    ) = when (entity) {
        "author" -> authorDao.add(name).let { "id = $it" }
        "genre" -> genreDao.add(name).let { "id = $it" }
        "book" -> bookDao.add(
            name,
            description,
            genreIds.split(",").map { it.toLong() },
            authorIds.split(",").map { it.toLong() }).let { "id = $it" }
        else -> "Wrong param"
    }

    @ShellMethod("Update entity")
    fun update(
        entity: String, id: Long, name: String,
        @ShellOption(defaultValue = "") description: String,
        @ShellOption(defaultValue = "") genreIds: String,
        @ShellOption(defaultValue = "") authorIds: String
    ) = when (entity) {
        "author" -> authorDao.update(id, name).let { "Updated" }
        "genre" -> genreDao.update(id, name).let { "Updated"  }
        "book" -> bookDao.update(
            id,
            name,
            description,
            genreIds.split(",").map { it.toLong() },
            authorIds.split(",").map { it.toLong() }).let { "Updated" }
        else -> "Wrong param"
    }

    @ShellMethod("Delete entity")
    fun delete(entity: String, id: Long) = when (entity) {
        "author" -> authorDao.delete(id).let { "Deleted" }
        "genre" -> genreDao.delete(id).let { "Deleted" }
        "book" -> bookDao.delete(id).let { "Deleted" }
        else -> "Wrong param"
    }

    @ShellMethod("Add review to book")
    fun review(bookId: Long, text: String) = reviewService.addReview(bookId, text)
}