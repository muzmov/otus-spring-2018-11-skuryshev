package ru.otus.bookstoreweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
@EnableCircuitBreaker
@EnableHystrixDashboard
class BookstoreWebApplication

fun main(args: Array<String>) {
    runApplication<BookstoreWebApplication>(*args)
}
