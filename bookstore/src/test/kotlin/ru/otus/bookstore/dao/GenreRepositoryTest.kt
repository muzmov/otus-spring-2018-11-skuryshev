package ru.otus.bookstore.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.bookstore.model.Genre

class GenreRepositoryTest: AbstractDaoTest() {
    @Autowired
    lateinit var genreRepository: GenreRepository

    @Test
    fun testAddDeleteUpdateGetAll() {
        val id1 = genreRepository.save(Genre(name = "TEST_NAME1")).id
        val id2 = genreRepository.save(Genre(name = "TEST_NAME2")).id
        val id3 = genreRepository.save(Genre(name = "TEST_NAME3")).id
        genreRepository.deleteById(id2)
        genreRepository.findById(id3).ifPresent {
            it.name = "TEST_NAME3_UPDATED"
            genreRepository.save(it)
        }
        val authors = genreRepository.findAll()
        assertThat(authors).containsExactlyInAnyOrder(
            Genre(id = id1, name = "TEST_NAME1"),
            Genre(id = id3, name = "TEST_NAME3_UPDATED"))
    }
}