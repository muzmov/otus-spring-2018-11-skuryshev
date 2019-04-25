package ru.otus.bookstoreweb.actuator

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.health.Status
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class BooksHealthIndicator(private var mongoTemplate: MongoTemplate) : HealthIndicator {

    override fun health() = try {
        mongoTemplate.executeCommand("{ buildInfo: 1 }");
        Health.up().build()
    } catch (e: Exception) {
        Health.down().status(Status.OUT_OF_SERVICE).withDetail("ERROR", e.message).build()
    }
}