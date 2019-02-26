package ru.otus.bookstoreweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Genre (
    @Id
    var id: String? = null,
    var name: String = "",
    var description: String = ""
)