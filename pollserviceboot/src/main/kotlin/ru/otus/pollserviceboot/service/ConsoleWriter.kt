package ru.otus.pollserviceboot.service

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import ru.otus.pollserviceboot.YamlProps
import java.util.*


@Service
class ConsoleWriter(
    private val props: YamlProps,
    private val messageSource: MessageSource
): Writer {

    override fun write(message: String, params: Array<Any>) {
        println(messageSource.getMessage(message, params, Locale.forLanguageTag(props.locale)))
    }
}