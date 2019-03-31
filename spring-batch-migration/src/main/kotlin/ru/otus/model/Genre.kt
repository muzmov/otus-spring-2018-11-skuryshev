package ru.otus.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.SequenceGenerator

@Document
@Entity
data class Genre (
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @SequenceGenerator(name = "genre_seq", sequenceName = "genre_seq", allocationSize = 1)
    var id: Long? = null,
    var name: String = ""
)