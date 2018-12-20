package ru.otus.service

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import java.util.*


@Service
class ConsoleWriter(
    private val locale: Locale,
    private val context: ApplicationContext
): Writer {

    override fun write(message: String, params: Array<Any>) {
        println(context.getMessage(message, params, locale))
    }
}