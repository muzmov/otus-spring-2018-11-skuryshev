package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Author
import ru.otus.bookstore.model.Book
import ru.otus.bookstore.model.Genre


class BookRepositoryTest : AbstractDaoTest() {
    @Autowired
    lateinit var authorRepository: AuthorRepository
    @Autowired
    lateinit var genreRepository: GenreRepository
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun testGetAllFromEmpty() {
        assertThat(bookRepository.findAll()).isEmpty()
    }

    @Test
    fun testAddDeleteUpdateGetAll() {
        val authors = authorRepository.saveAll(listOf(Author(name = "TEST_AUTHOR1"), Author(name = "TEST_AUTHOR2"))).toMutableSet()
        val genres = genreRepository.saveAll(listOf(Genre(name = "TEST_GENRE1"), Genre(name = "TEST_GENRE2"))).toMutableSet()
        val id1 = bookRepository.save(Book(title = "TEST_TITLE1", description = "TEST_DESCRIPTION1").also {
            it.genres = genres
            it.authors = authors
        }).id
        val id2 = bookRepository.save(Book(title = "TEST_TITLE2", description = "TEST_DESCRIPTION2").also {
            it.genres = genres
            it.authors = authors
        }).id
        val id3 = bookRepository.save(Book(title = "TEST_TITLE3", description = "TEST_DESCRIPTION3").also {
            it.genres = mutableSetOf()
            it.authors = mutableSetOf()
        }).id
        bookRepository.deleteById(id2)
        bookRepository.findById(id3).ifPresent{
            it.title = "TEST_TITLE3_UPDATED"
            it.description = "TEST_DESCRIPTION3_UPDATED"
            it.genres = genres
            it.authors = authors
            bookRepository.save(it)
        }

        val books = bookRepository.findAll()

        assertThat(books).containsExactlyInAnyOrder(
            Book(id1, "TEST_TITLE1", "TEST_DESCRIPTION1"),
            Book(id3, "TEST_TITLE3_UPDATED", "TEST_DESCRIPTION3_UPDATED")
        )
        assertThat(books.find { it.id == id1 }?.authors).extracting<String> { it.name }.containsExactlyInAnyOrder("TEST_AUTHOR1", "TEST_AUTHOR2")
        assertThat(books.find { it.id == id3 }?.authors).extracting<String> { it.name }.containsExactlyInAnyOrder("TEST_AUTHOR1", "TEST_AUTHOR2")

        assertThat(books.find { it.id == id1 }?.genres).extracting<String> { it.name }.containsExactlyInAnyOrder("TEST_GENRE1", "TEST_GENRE2")
        assertThat(books.find { it.id == id3 }?.genres).extracting<String> { it.name }.containsExactlyInAnyOrder("TEST_GENRE1", "TEST_GENRE2")
    }
}