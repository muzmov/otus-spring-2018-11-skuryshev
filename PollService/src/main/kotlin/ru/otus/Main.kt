package ru.otus

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.service.PollService

fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext(Config::class.java)
    val pollService = context.getBean(PollService::class.java)
    pollService.doPoll()
}

