package ru.otus.pollserviceboot.service

interface Writer {
    fun write(message: String, params: Array<Any>)
}