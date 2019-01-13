package ru.otus.bookstore.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Genre

@Repository
class GenreDao {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): List<Genre> = jdbcTemplate.query("select * from genre", BeanPropertyRowMapper(Genre::class.java))

    fun add(name: String): Long = jdbcTemplate.run {
        val id = queryForObject("select genre_seq.nextval from dual", Long::class.java)
        update("insert into genre (id, name) values (?, ?)", id, name)
        id!!
    }

    fun update(id: Long, name: String) {
        jdbcTemplate.update("update genre set name = ? where id = ?", name, id)
    }

    fun delete(id: Long) {
        jdbcTemplate.apply {
            update("delete from book_genre where genre_id = ?", id)
            update("delete from genre where id = ?", id)
        }
    }
}