package ru.otus.bookstore.dao

import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(properties = [InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"])
abstract class AbstractDaoTest {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Before
    fun setUp() {
        jdbcTemplate.update("delete from book_author")
        jdbcTemplate.update("delete from book_genre")
        jdbcTemplate.update("delete from book")
        jdbcTemplate.update("delete from author")
        jdbcTemplate.update("delete from genre")
    }
}