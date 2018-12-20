package ru.otus.writer

class ConsoleWriter: Writer {

    override fun write(s: String) {
        println(s)
    }
}