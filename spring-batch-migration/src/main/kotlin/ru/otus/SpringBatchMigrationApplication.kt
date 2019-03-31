package ru.otus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBatchMigrationApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchMigrationApplication>(*args)
}
