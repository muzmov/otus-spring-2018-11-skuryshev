package ru.otus.crudrest.controller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.otus.crudrest.model.Author
import ru.otus.crudrest.service.AuthorService

@RestController
class MainController(
    val authorService: AuthorService
) {

    @GetMapping("/authors")
    fun authors(model: Model) = authorService.findAll()

    @GetMapping("/author/{id}")
    fun author(model: Model, @PathVariable id: Long) = authorService.findById(id)

    @PostMapping("/author")
    fun updateAuthor(@RequestBody author: Author): Status = authorService.update(author).let { Status.SUCCESS }

    @PutMapping("/author")
    fun createAuthor(@RequestBody author: Author): Status = authorService.create(author).let { Status.SUCCESS }

    @DeleteMapping("/author/{id}")
    fun deleteAuthor(@PathVariable id: Long) = authorService.delete(authorService.findById(id)).let { Status.SUCCESS }
}

