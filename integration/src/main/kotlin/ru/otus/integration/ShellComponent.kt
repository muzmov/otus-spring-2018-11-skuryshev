package ru.otus.integration

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import org.springframework.transaction.annotation.Transactional
import ru.otus.integration.model.Book
import ru.otus.integration.service.BookService


@ShellComponent
@Transactional
class ShellComponent(
    private val bookService: BookService
) {

    @ShellMethod("List books")
    fun list() = bookService.findAll().joinToString(separator = "\n")

    @ShellMethod("Add entity")
    fun add(@ShellOption(defaultValue = "") var1: String, @ShellOption(defaultValue = "") var2: String) =
        bookService.add(Book(title = var1, description = var2)).let { "book created" }

}