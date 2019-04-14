package ru.otus.integration.service

import org.springframework.messaging.Message
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.otus.integration.dao.BookRepository
import ru.otus.integration.model.Book
import javax.annotation.PostConstruct

@Component
class BookService(
    private val bookRepository: BookRepository,
    private val addAuthorsChannel: SubscribableChannel,
    private val authorsAddedChannel: SubscribableChannel,
    private val addGenresChannel: SubscribableChannel,
    private val genresAddedChannel: SubscribableChannel
) {

    @PostConstruct
    private fun init() {
        authorsAddedChannel.subscribe { addGenresChannel.send(it) }
        genresAddedChannel.subscribe { save(it) }
    }

    fun findAll() = bookRepository.findAll()

    fun add(book: Book) {
        addAuthorsChannel.send(MessageBuilder.withPayload(book).build())
    }

    fun save(message: Message<*>) = bookRepository.save(message.payload as? Book ?: throw IllegalArgumentException("wrong message"))
}