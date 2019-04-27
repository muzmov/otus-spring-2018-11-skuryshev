package ru.otus.bookstoreweb.controller

import io.micrometer.core.instrument.Metrics
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import ru.otus.bookstoreweb.dao.AuthorRepository
import ru.otus.bookstoreweb.dao.BookRepository
import ru.otus.bookstoreweb.dao.GenreRepository
import ru.otus.bookstoreweb.exception.NotFoundException
import ru.otus.bookstoreweb.model.Author
import ru.otus.bookstoreweb.model.Book
import ru.otus.bookstoreweb.model.BookDto
import ru.otus.bookstoreweb.model.Genre
import ru.otus.bookstoreweb.service.AuthorService
import ru.otus.bookstoreweb.service.BookService
import ru.otus.bookstoreweb.service.GenreService

@Controller
class MainController(
    val bookService: BookService,
    val authorService: AuthorService,
    val genreService: GenreService
) {

    private val bookRequestCounter = Metrics.counter("book.request")

    @GetMapping("/authors")
    fun authors(model: Model): String {
        model.addAttribute("authors", authorService.findAll())
        return "authors"
    }

    @GetMapping("/genres")
    fun genres(model: Model): String {
        model.addAttribute("genres", genreService.findAll())
        return "genres"
    }

    @GetMapping("/books")
    fun books(model: Model): String {
        model.addAttribute("books", bookService.findAll())
        return "books"
    }

    @GetMapping("/author/{id}", "/author")
    fun editAuthor(model: Model, @PathVariable id: String?): String {
        val author = if (id != null) {
            authorService.findById(id).orElseThrow { NotFoundException("author not found") }
        } else {
            Author()
        }
        model.addAttribute("author", author)
        return "author"
    }

    @PostMapping("/author")
    fun updateAuthor(author: Author): String {
        if (author.id?.isBlank()!!) {
            authorService.save(author.copy(id = null))
        } else {
            authorService.save(author)
        }
        return "redirect:/authors"
    }

    @GetMapping("/genre/{id}", "/genre")
    fun editGenre(model: Model, @PathVariable id: String?): String {
        val genre = if (id != null) {
            genreService.findById(id).orElseThrow { NotFoundException("genre not found") }
        } else {
            Genre()
        }
        model.addAttribute("genre", genre)
        return "genre"
    }

    @PostMapping("/genre")
    fun updateGenre(genre: Genre): String {
        if (genre.id?.isBlank()!!) {
            genreService.save(genre.copy(id = null))
        } else {
            genreService.save(genre)
        }
        return "redirect:/genres"
    }

    @GetMapping("/book/{id}", "/book")
    fun getBook(model: Model, @PathVariable id: String?): String {
        val book = if (id != null) {
            bookRequestCounter.increment()
            bookService.findById(id).orElseThrow { NotFoundException("book not found") }
        } else {
            Book()
        }
        model.addAttribute("book", BookDto.fromBook(book))
        return "book"
    }

    @PostMapping("/book")
    fun updateBook(book: BookDto): String {
        val id = if (book.id?.isBlank()!!) null else book.id
        val authors = book.authorIds.split(",").filter { !it.isBlank() }
            .map { authorService.findById(it.trim()).orElseThrow { NotFoundException("author not found") } }
        val genres = book.genreIds.split(",").filter { !it.isBlank() }
            .map { genreService.findById(it.trim()).orElseThrow { NotFoundException("genre not found") } }
        bookService.save(Book(id = id, title = book.title, description = book.description).also {
            it.authors = authors.toMutableSet()
            it.genres = genres.toMutableSet()
        })
        return "redirect:/books"
    }

    @GetMapping("/delete-author/{id}")
    fun deleteAuthor(@PathVariable id: String): String {
        authorService.deleteById(id)
        return "redirect:/authors"
    }

    @GetMapping("/delete-genre/{id}")
    fun deleteGenre(@PathVariable id: String): String {
        genreService.deleteById(id)
        return "redirect:/genres"
    }

    @GetMapping("/delete-book/{id}")
    fun deleteBook(@PathVariable id: String): String {
        bookService.deleteById(id)
        return "redirect:/books"
    }

}
