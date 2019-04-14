package ru.otus.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*

@Document
@Entity
data class Book(
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    var id: Long? = null,
    var title: String = "",
    var description: String = ""
) {

    @DBRef
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_genre",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")]
    )
    var genres: MutableSet<Genre> = mutableSetOf()
    @DBRef
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")]
    )
    var authors: MutableSet<Author> = mutableSetOf()

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', genres=$genres, authors=$authors)"
    }
}