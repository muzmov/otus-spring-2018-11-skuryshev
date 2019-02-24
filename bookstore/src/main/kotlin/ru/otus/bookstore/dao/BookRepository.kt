package ru.otus.bookstore.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.bookstore.model.Book

@Repository
@Transactional
interface BookRepository: JpaRepository<Book, Long>