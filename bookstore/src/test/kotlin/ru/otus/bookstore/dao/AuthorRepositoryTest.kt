package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Author

class AuthorRepositoryTest: AbstractDaoTest() {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Test
    fun testAddDeleteUpdateGetAll() {
        val id1 = authorRepository.save(Author(firstName = "TEST_NAME1")).id!!
        val id2 = authorRepository.save(Author(firstName = "TEST_NAME2")).id!!
        val id3 = authorRepository.save(Author(firstName = "TEST_NAME3")).id!!
        authorRepository.deleteById(id2)
        authorRepository.findById(id3).ifPresent {
            it.firstName = "TEST_NAME3_UPDATED"
            authorRepository.save(it)
        }
        val authors = authorRepository.findAll()
        assertThat(authors).containsExactlyInAnyOrder(
            Author(id = id1, firstName = "TEST_NAME1"),
            Author(id = id3, firstName = "TEST_NAME3_UPDATED"))
    }
}