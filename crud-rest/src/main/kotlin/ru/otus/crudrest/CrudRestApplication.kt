package ru.otus.crudrest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class CrudRestApplication

fun main(args: Array<String>) {
    runApplication<CrudRestApplication>(*args)
}
