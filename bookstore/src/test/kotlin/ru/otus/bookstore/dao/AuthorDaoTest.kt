package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Author

class AuthorDaoTest: AbstractDaoTest() {
    @Autowired
    lateinit var authorDao: AuthorDao

    @Test
    fun testAddDeleteUpdateGetAll() {
        val id1 = authorDao.add("TEST_NAME1")
        val id2 = authorDao.add("TEST_NAME2")
        val id3 = authorDao.add("TEST_NAME3")
        authorDao.delete(id2)
        authorDao.update(id3, "TEST_NAME3_UPDATED")
        val authors = authorDao.getAll()
        assertThat(authors).containsExactlyInAnyOrder(
            Author(id = id1, name = "TEST_NAME1"),
            Author(id = id3, name = "TEST_NAME3_UPDATED"))
    }
}