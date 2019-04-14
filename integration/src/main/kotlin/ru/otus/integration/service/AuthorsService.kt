package ru.otus.integration.service

import org.springframework.messaging.Message
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.otus.integration.dao.AuthorRepository
import ru.otus.integration.model.Author
import ru.otus.integration.model.Book
import javax.annotation.PostConstruct

@Component
class AuthorsService(
    private val authorRepository: AuthorRepository,
    private val addAuthorsChannel: SubscribableChannel,
    private val authorsAddedChannel: SubscribableChannel
) {

    @PostConstruct
    private fun init() {
        addAuthorsChannel.subscribe { addAuthors(it) }
    }

    private fun addAuthors(message: Message<*>) {
        val book = message.payload as? Book ?: throw IllegalArgumentException("Wrong message")
        while (addAuthor(book)) {}
        authorsAddedChannel.send(MessageBuilder.withPayload(book).build())
    }

    private fun addAuthor(book: Book): Boolean {
        println("enter author credentials or 'quit' to finish:")
        val credentials = readLine().let {
            if (it == null || it == "quit") return false
            it.split(" ")
        }
        val author = getOrSaveAuthor(credentials)
        book.authors.add(author)
        return true
    }

    private fun getOrSaveAuthor(fio: List<String>) = authorRepository.findByCredentials(fio[0], fio[1], fio[2]) ?:
        authorRepository.save(Author(firstName = fio[0], lastName = fio[1], middleName = fio[2]))
}