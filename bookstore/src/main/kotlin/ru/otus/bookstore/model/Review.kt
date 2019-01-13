package ru.otus.bookstore.model

import javax.persistence.*

@Entity
data class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @SequenceGenerator(name = "genre_seq", sequenceName = "genre_seq", allocationSize = 1)
    var id: Long = 0,
    var text: String = "",

    @ManyToOne
    @JoinColumn(name = "book_id")
    var book: Book? = null
)