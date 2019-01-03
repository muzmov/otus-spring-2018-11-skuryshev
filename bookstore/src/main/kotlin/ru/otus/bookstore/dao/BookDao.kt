package ru.otus.bookstore.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.otus.bookstore.model.Author
import ru.otus.bookstore.model.Book
import ru.otus.bookstore.model.Genre
import java.sql.PreparedStatement

@Repository
class BookDao {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): Set<Book> = jdbcTemplate.run {
        val books: MutableSet<Book> = mutableSetOf()
        query("""select b.id b_id, b.title b_title, b.description b_description, a.id a_id, a.name a_name, g.id g_id, g.name g_name from book b
            |left join book_author ba on b.id = ba.book_id left join author a on ba.author_id = a.id
            |left join book_genre bg on bg.book_id = b.id left join genre g on bg.genre_id = g.id""".trimMargin()) {
            var book = Book(
                id = it.getLong("id"),
                title = it.getString("title"),
                description = it.getString("description")
            )
            val author = Author(
                id = it.getLong("a_id"),
                name = it.getString("a_name")
            )
            val genre = Genre(
                id = it.getLong("g_id"),
                name = it.getString("g_name")
            )
            if (!books.add(book)) {
                book = books.find { it.id == book.id }!!
            }
            book.authors.add(author)
            book.genres.add(genre)
        }
        books
    }

    fun add(title: String, description: String, genreIds: List<Long>, authorIds: List<Long>): Long = jdbcTemplate.run {
        val id = queryForObject("select book_seq.nextval from dual", Long::class.java)!!
        update("insert into book(id, title, description) values (?, ?, ?)", id, title, description)
        batchUpdate("insert into book_genre (book_id, genre_id) values (?, ?)", object: BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                ps.setLong(1, id)
                ps.setLong(2, genreIds[i])
            }

            override fun getBatchSize() = genreIds.size
        })
        batchUpdate("insert into book_author (book_id, author_id) values (?, ?)", object: BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                ps.setLong(1, id)
                ps.setLong(2, authorIds[i])
            }

            override fun getBatchSize() = authorIds.size
        })
        id
    }

    fun update(id: Long, title: String, description: String, genreIds: List<Long>, authorIds: List<Long>) {
        jdbcTemplate.apply {
            update("update book set title = ?, description = ? where id = ?", title, description, id)
            update("delete from book_author where book_id = ?", id)
            update("delete from book_genre where book_id = ?", id)
            batchUpdate("insert into book_genre (book_id, genre_id) values (?, ?)", object: BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, id)
                    ps.setLong(2, genreIds[i])
                }

                override fun getBatchSize() = genreIds.size
            })
            batchUpdate("insert into book_author (book_id, author_id) values (?, ?)", object: BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, id)
                    ps.setLong(2, authorIds[i])
                }

                override fun getBatchSize() = authorIds.size
            })
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