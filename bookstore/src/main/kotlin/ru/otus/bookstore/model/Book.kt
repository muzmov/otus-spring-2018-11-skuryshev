package ru.otus.bookstore.model

import javax.persistence.*

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    var id: Long = 0,
    var title: String = "",
    var description: String = ""
) {
    @ManyToMany
    @JoinTable(
        name = "book_genre",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")]
    )
    var genres: MutableSet<Genre> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")]
    )
    var authors: MutableSet<Author> = mutableSetOf()

    @OneToMany(mappedBy = "book")
    var reviews: MutableSet<Review> = mutableSetOf()

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', genres=$genres, authors=$authors, reviews=[${reviews.map { it.text }.joinToString()}])"
    }
}