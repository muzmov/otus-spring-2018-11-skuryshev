package ru.otus.crudrest.controller

data class Status(val status: String) {
    companion object {
        val SUCCESS = Status("success")
    }
}