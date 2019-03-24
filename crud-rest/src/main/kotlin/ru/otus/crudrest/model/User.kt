package ru.otus.crudrest.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    var id: String? = null,
    var username: String = "",
    var password: String = ""
)