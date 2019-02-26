package ru.otus.bookstoreweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Author(
    @Id
    var id: String? = null,
    var firstName: String = "",
    var lastName: String = "",
    var middleName: String = ""
)