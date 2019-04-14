package ru.otus.integration.service

import org.springframework.messaging.Message
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.otus.integration.dao.GenreRepository
import ru.otus.integration.model.Book
import ru.otus.integration.model.Genre
import javax.annotation.PostConstruct

@Component
class GenresService(
    private val genreRepository: GenreRepository,
    private val addGenresChannel: SubscribableChannel,
    private val genresAddedChannel: SubscribableChannel
) {

    @PostConstruct
    private fun init() {
        addGenresChannel.subscribe { addGenres(it) }
    }

    private fun addGenres(message: Message<*>) {
        val book = message.payload as? Book ?: throw IllegalArgumentException("Wrong message")
        while (addGenre(book)) {}
        genresAddedChannel.send(MessageBuilder.withPayload(book).build())
    }

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