package ru.otus.service

interface Writer {
    fun write(message: String, params: Array<Any>)
}