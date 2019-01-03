package ru.otus.bookstore.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Author
import ru.otus.bookstore.model.Book
import ru.otus.bookstore.model.Genre

@Repository
class BookDao {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): List<Book> = jdbcTemplate.run {
        val books: MutableList<Book> = mutableListOf()
        query("select * from book") {
            val book = Book(
                id = it.getLong("id"),
                title = it.getString("title"),
                description = it.getString("description")
            )
            val authors: MutableList<Author> = mutableListOf()
            query("select * from book_author ba join author a on ba.author_id = a.id where ba.book_id = ?", book.id) {
                authors += Author(id = it.getLong("id"), name = it.getString("name"))
            }
            val genres: MutableList<Genre> = mutableListOf()
            query("select * from book_genre bg join genre g on bg.genre_id = g.id where bg.book_id = ?", book.id) {
                genres += Genre(id = it.getLong("id"), name = it.getString("name"))
            }
            book.authors = authors
            book.genres = genres
            books += book
        }
        books
    }

    fun add(title: String, description: String, genreIds: List<Long>, authorIds: List<Long>): Long = jdbcTemplate.run {
        val id = queryForObject("select book_seq.nextval from dual", Long::class.java)
        update("insert into book(id, title, description) values (?, ?, ?)", id, title, description)
        genreIds.forEach { update("insert into book_genre (book_id, genre_id) values (?, ?)", id, it) }
        authorIds.forEach { update("insert into book_author (book_id, author_id) values (?, ?)", id, it) }
        id!!
    }


    fun update(id: Long, title: String, description: String, genreIds: List<Long>, authorIds: List<Long>) {
        jdbcTemplate.apply {
            update("update book set title = ?, description = ? where id = ?", title, description, id)
            update("delete from book_author where book_id = ?", id)
            update("delete from book_genre where book_id = ?", id)
            genreIds.forEach { update("insert into book_genre (book_id, genre_id) values (?, ?)", id, it) }
            authorIds.forEach { update("insert into book_author (book_id, author_id) values (?, ?)", id, it) }
        }
    }

    fun delete(id: Long) {
        jdbcTemplate.apply {
            update("delete from book_author where book_id = ?", id)
            update("delete from book_genre where book_id = ?", id)
            update("delete from book where id = ?", id)
        }
    }
}