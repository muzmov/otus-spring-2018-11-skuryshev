package ru.otus.service

import org.springframework.stereotype.Service

@Service
class ConsoleWriter: Writer {

    override fun write(s: String) {
        println(s)
    }
}