package ru.otus.bookstore.dao

import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(properties = [InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"])
abstract class AbstractDaoTest {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Before
    fun setUp() {
        mongoTemplate.db.getCollection("book").drop()
        mongoTemplate.db.getCollection("genre").drop()
        mongoTemplate.db.getCollection("author").drop()
    }
}