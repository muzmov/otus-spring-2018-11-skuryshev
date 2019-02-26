package ru.otus.bookstoreweb.model

data class BookDto(
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var genreIds: String = "",
    var authorIds: String = ""
) {
    companion object {
        fun fromBook(book: Book) = BookDto(
            id = book.id,
            title = book.title,
            description = book.description,
            genreIds = book.genres.joinToString {it.id.toString()},
            authorIds =  book.authors.joinToString {it.id.toString()}
        )
    }
}