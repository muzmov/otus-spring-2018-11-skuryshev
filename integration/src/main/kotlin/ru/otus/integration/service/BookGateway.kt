package ru.otus.integration.service

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway
import ru.otus.integration.model.Book

@MessagingGateway
interface BookGateway {

    @Gateway(requestChannel = "newBookChannel", replyChannel = "bookChannel")
    fun newBook(book: Book): Book
}