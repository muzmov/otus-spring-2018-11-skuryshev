package ru.otus.integration.service

import org.springframework.stereotype.Component
import ru.otus.integration.dao.GenreRepository
import ru.otus.integration.model.Book
import ru.otus.integration.model.Genre

@Component
class GenresService(private val genreRepository: GenreRepository) {

    fun addGenres(book: Book) = book.also { while (addGenre(it)) {} }

    private fun addGenre(book: Book): Boolean {
        println("enter genre name and description or 'quit' to finish:")
        val (name, description) = readLine().let {
            if (it == null || it == "quit") return false
            it.split(" ")
        }
        val genre = getOrSaveGenre(name, description)
        book.genres.add(genre)
        return true
    }

    private fun getOrSaveGenre(name: String, description: String) =
        genreRepository.findByName(name) ?: genreRepository.save(Genre(name = name, description = description))
}