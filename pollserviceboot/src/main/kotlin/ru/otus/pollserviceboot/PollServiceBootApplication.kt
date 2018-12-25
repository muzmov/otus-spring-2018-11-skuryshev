package ru.otus.pollserviceboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.otus.pollserviceboot.service.PollService

@SpringBootApplication
class PollServiceBootApplication

fun main(args: Array<String>) {
    val context = runApplication<PollServiceBootApplication>(*args)
    context.getBean(PollService::class.java).doPoll()
}

