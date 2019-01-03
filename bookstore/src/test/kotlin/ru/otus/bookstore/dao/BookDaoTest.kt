package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Author
import ru.otus.bookstore.model.Book
import ru.otus.bookstore.model.Genre


class BookDaoTest: AbstractDaoTest() {
    @Autowired
    lateinit var authorDao: AuthorDao
    @Autowired
    lateinit var genreDao: GenreDao
    @Autowired
    lateinit var bookDao: BookDao

    @Test
    fun testGetAllFromEmpty() {
        assertThat(bookDao.getAll()).isEmpty()
    }

    @Test
    fun testAddDeleteUpdateGetAll() {
        val authorIds = listOf(authorDao.add("TEST_AUTHOR1"), authorDao.add("TEST_AUTHOR2"))
        val genreIds = listOf(genreDao.add("TEST_GENRE1"), genreDao.add("TEST_GENRE2"))
        val id1 = bookDao.add("TEST_TITLE1", "TEST_DESCRIPTION1", genreIds, authorIds)
        val id2 = bookDao.add("TEST_TITLE2", "TEST_DESCRIPTION2", genreIds, authorIds)
        val id3 = bookDao.add("TEST_TITLE3", "TEST_DESCRIPTION3", listOf(), listOf())
        bookDao.delete(id2)
        bookDao.update(id3, "TEST_TITLE3_UPDATED", "TEST_DESCRIPTION3_UPDATED", genreIds, authorIds)

        val books = bookDao.getAll()

        assertThat(books).containsExactlyInAnyOrder(
            Book(id1, "TEST_TITLE1", "TEST_DESCRIPTION1"),
            Book(id3, "TEST_TITLE3_UPDATED", "TEST_DESCRIPTION3_UPDATED")
        )
        assertThat(books.find { it.id == id1 } ?.authors).containsExactlyInAnyOrder(
            Author(authorIds[0], "TEST_AUTHOR1"),
            Author(authorIds[1], "TEST_AUTHOR2")
        )
        assertThat(books.find { it.id == id3 } ?.authors).containsExactlyInAnyOrder(
            Author(authorIds[0], "TEST_AUTHOR1"),
            Author(authorIds[1], "TEST_AUTHOR2")
        )
        assertThat(books.find { it.id == id1 } ?.genres).containsExactlyInAnyOrder(
            Genre(genreIds[0], "TEST_GENRE1"),
            Genre(genreIds[1], "TEST_GENRE2")
        )
        assertThat(books.find { it.id == id3 } ?.genres).containsExactlyInAnyOrder(
            Genre(genreIds[0], "TEST_GENRE1"),
            Genre(genreIds[1], "TEST_GENRE2")
        )
    }
}