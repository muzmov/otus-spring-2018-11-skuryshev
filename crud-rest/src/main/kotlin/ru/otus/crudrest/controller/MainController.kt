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
    fun updateAuthor(@RequestBody author: Author) = authorRepository.save(author).let { Status.SUCCESS }

    @PutMapping("/author")
    fun createAuthor(@RequestBody author: Author) = authorRepository.save(author.copy(id = null)).let { Status.SUCCESS }

    @DeleteMapping("/author/{id}")
    fun deleteAuthor(@PathVariable id: String) = authorRepository.deleteById(id).let { Status.SUCCESS }
}

data class Status(val status: String) {
    companion object {
        val SUCCESS = Status("success")
    }
}
