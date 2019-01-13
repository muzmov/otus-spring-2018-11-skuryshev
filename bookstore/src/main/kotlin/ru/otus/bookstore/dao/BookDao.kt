package ru.otus.bookstore.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Book
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class BookDao {

    @PersistenceContext
    lateinit var em: EntityManager
    @Autowired
    lateinit var authorDao: AuthorDao
    @Autowired
    lateinit var genreDao: GenreDao

    fun getById(id: Long) = em.find(Book::class.java, id)

    fun getAll(): List<Book> = em.createQuery(
        """select distinct b from Book b
            left join fetch b.genres
            left join fetch b.authors
            left join fetch b.reviews
        """.trimIndent(), Book::class.java
    ).resultList

    fun add(title: String, description: String, genreIds: List<Long>, authorIds: List<Long>) =
        Book(title = title, description = description).also {
            it.authors = authorDao.getList(authorIds).toMutableSet()
            it.genres = genreDao.getList(genreIds).toMutableSet()
            em.persist(it)
        }.id

    fun update(id: Long, title: String, description: String, genreIds: List<Long>, authorIds: List<Long>) =
        em.find(Book::class.java, id)?.let {
            it.title = title
            it.description = description
            it.authors = authorDao.getList(authorIds).toMutableSet()
            it.genres = genreDao.getList(genreIds).toMutableSet()
        }


    fun delete(id: Long) {
        em.createQuery("delete from Book b where b.id = :id").setParameter("id", id).executeUpdate()
    }
}