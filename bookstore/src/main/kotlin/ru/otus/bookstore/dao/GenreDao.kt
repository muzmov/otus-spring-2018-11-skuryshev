package ru.otus.bookstore.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Genre
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class GenreDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getAll(): List<Genre> = em.createQuery("select g from Genre g", Genre::class.java).resultList

    fun getList(ids: List<Long>) = em.createQuery("select a from Genre a where a.id in :ids", Genre::class.java).setParameter("ids", ids).resultList

    fun add(name: String)= Genre(name = name).also { em.persist(it) }.id

    fun update(id: Long, name: String) = em.find(Genre::class.java, id)?.let { it.name = name }

    fun delete(id: Long) {
        em.createQuery("delete from Genre g where g.id = :id").setParameter("id", id).executeUpdate()
    }
}