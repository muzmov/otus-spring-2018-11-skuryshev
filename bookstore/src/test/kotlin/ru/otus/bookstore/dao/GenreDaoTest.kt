package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Genre

class GenreDaoTest: AbstractDaoTest() {
    @Autowired
    lateinit var genreDao: GenreDao

    @Test
    fun testAddDeleteUpdateGetAll() {
        val id1 = genreDao.add("TEST_NAME1")
        val id2 = genreDao.add("TEST_NAME2")
        val id3 = genreDao.add("TEST_NAME3")
        genreDao.delete(id2)
        genreDao.update(id3, "TEST_NAME3_UPDATED")
        val authors = genreDao.getAll()
        assertThat(authors).containsExactlyInAnyOrder(
            Genre(id = id1, name = "TEST_NAME1"),
            Genre(id = id3, name = "TEST_NAME3_UPDATED"))
    }
}