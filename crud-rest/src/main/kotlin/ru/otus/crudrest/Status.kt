package ru.otus.crudrest

import reactor.core.publisher.Mono

data class Status(val status: String) {
    companion object {
        val SUCCESS = Mono.from<Status> { Status("success") }
    }
}