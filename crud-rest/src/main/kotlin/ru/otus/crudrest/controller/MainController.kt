package ru.otus.crudrest.controller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.otus.crudrest.dao.AuthorRepository
import ru.otus.crudrest.exception.NotFoundException
import ru.otus.crudrest.model.Author

@RestController
class MainController(
    val authorRepository: AuthorRepository
) {

    @GetMapping("/authors")
    fun authors(model: Model) = authorRepository.findAll()

    @GetMapping("/author/{id}", "/author")
    fun author(model: Model, @PathVariable id: String?) = if (id != null) {
        authorRepository.findById(id).orElseThrow { NotFoundException("author not found") }
    } else {
        Author()
    }

    @PostMapping("/author")
    fun updateAuthor(@RequestBody author: Author): String {
        if (author.id?.isBlank()!!) {
            authorRepository.save(author.copy(id = null))
        } else {
            authorRepository.save(author)
        }
        return """{"status": "success"}"""
    }

    @DeleteMapping("/author/{id}")
    fun deleteAuthor(@PathVariable id: String): String {
        authorRepository.deleteById(id)
        return """{"status": "success"}"""
    }
}
