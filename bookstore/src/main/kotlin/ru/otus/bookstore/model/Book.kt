package ru.otus.bookstore.model

data class Book (
    var id: Long = 0,
    var title: String = "",
    var description: String = ""
) {
    var genres: List<Genre> = listOf()
    var authors: List<Author> = listOf()

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', genres=$genres, authors=$authors)"
    }
}