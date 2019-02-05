package ru.otus.bookstore.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Author
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class AuthorDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getAll(): List<Author> = em.createQuery("select a from Author a", Author::class.java).resultList

    fun getList(ids: List<Long>) = em.createQuery("select a from Author a where a.id in :ids", Author::class.java).setParameter("ids", ids).resultList

    fun add(name: String) = Author(name = name).also { em.persist(it) }.id

    fun update(id: Long, name: String) = em.find(Author::class.java, id)?.let { it.name = name }

    fun delete(id: Long) {
        em.createQuery("delete from Author a where a.id = :id").setParameter("id", id).executeUpdate()
    }
}