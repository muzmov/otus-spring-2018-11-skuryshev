package ru.otus.bookstore.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Author

@Repository
class AuthorDao {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): List<Author> = jdbcTemplate.query("select * from author", BeanPropertyRowMapper(Author::class.java))

    fun add(name: String): Long = jdbcTemplate.run {
        val id = queryForObject("select author_seq.nextval from dual", Long::class.java)
        update("insert into author (id, name) values (?, ?)", id, name)
        id!!
    }


    fun update(id: Long, name: String) {
        jdbcTemplate.update("update author set name = ? where id = ?", name, id)
    }

    fun delete(id: Long) {
        jdbcTemplate.apply {
            update("delete from book_author where author_id = ?", id)
            update("delete from author where id = ?", id)
        }
    }
}