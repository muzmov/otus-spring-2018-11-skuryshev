package ru.otus

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.service.PollService

fun main(args: Array<String>) {
    val context = ClassPathXmlApplicationContext("/context.xml")
    val pollService = context.getBean(PollService::class.java)
    pollService.doPoll()
}

