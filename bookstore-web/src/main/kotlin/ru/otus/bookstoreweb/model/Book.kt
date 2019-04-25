package ru.otus.bookstoreweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Book(
    @Id
    var id: String? = null,
    var title: String = "",
    var description: String = ""
) {

    @DBRef
    var genres: MutableSet<Genre> = mutableSetOf()
    @DBRef
    var authors: MutableSet<Author> = mutableSetOf()

    fun genreNames() = genres.joinToString {it.name }
    fun authorsFio() = authors.joinToString { "${it.firstName.firstOrNull() ?: ""}. ${it.middleName.firstOrNull() ?: ""}. ${it.lastName}"  }

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', genres=$genres, authors=$authors)"
    }
}