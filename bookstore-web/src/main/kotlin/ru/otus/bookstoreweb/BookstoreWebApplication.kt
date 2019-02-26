package ru.otus.bookstoreweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class BookstoreWebApplication

fun main(args: Array<String>) {
    runApplication<BookstoreWebApplication>(*args)
}
