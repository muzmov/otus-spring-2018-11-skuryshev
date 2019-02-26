package ru.otus.bookstoreweb

import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.otus.bookstoreweb.dao.AuthorRepository
import ru.otus.bookstoreweb.dao.BookRepository
import ru.otus.bookstoreweb.dao.GenreRepository
import ru.otus.bookstoreweb.model.Author
import ru.otus.bookstoreweb.model.Book
import ru.otus.bookstoreweb.model.BookDto
import ru.otus.bookstoreweb.model.Genre
import java.util.*

@RunWith(SpringRunner::class)
@WebMvcTest
class BookstoreWebApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var authorRepository: AuthorRepository
    @MockBean
    lateinit var genreRepository: GenreRepository
    @MockBean
    lateinit var bookRepository: BookRepository

    @Test
    fun testGetAuthors() {
        whenever(authorRepository.findAll()).thenReturn(listOf(Author(id = "test")))
        mockMvc.perform(get("/authors"))
            .andExpect(status().isOk)
            .andExpect(view().name("authors"))
            .andExpect(model().attribute("authors", listOf(Author(id = "test"))))
    }

    @Test
    fun testGetGenres() {
        whenever(genreRepository.findAll()).thenReturn(listOf(Genre(id = "test")))
        mockMvc.perform(get("/genres"))
            .andExpect(status().isOk)
            .andExpect(view().name("genres"))
            .andExpect(model().attribute("genres", listOf(Genre(id = "test"))))
    }

    @Test
    fun testGetBooks() {
        whenever(bookRepository.findAll()).thenReturn(listOf(Book(id = "test")))
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(view().name("books"))
            .andExpect(model().attribute("books", listOf(Book(id = "test"))))
    }

    @Test
    fun testGetAuthor() {
        whenever(authorRepository.findById("test")).thenReturn(Optional.of(Author(id = "test")))
        mockMvc.perform(get("/author/test"))
            .andExpect(status().isOk)
            .andExpect(view().name("author"))
            .andExpect(model().attribute("author", Author(id = "test")))
    }

    @Test
    fun testGetGenre() {
        whenever(genreRepository.findById("test")).thenReturn(Optional.of(Genre(id = "test")))
        mockMvc.perform(get("/genre/test"))
            .andExpect(status().isOk)
            .andExpect(view().name("genre"))
            .andExpect(model().attribute("genre", Genre(id = "test")))
    }

    @Test
    fun testGetBook() {
        whenever(bookRepository.findById("test")).thenReturn(Optional.of(Book(id = "test")))
        mockMvc.perform(get("/book/test"))
            .andExpect(status().isOk)
            .andExpect(view().name("book"))
            .andExpect(model().attribute("book", BookDto(id = "test")))
    }
}
