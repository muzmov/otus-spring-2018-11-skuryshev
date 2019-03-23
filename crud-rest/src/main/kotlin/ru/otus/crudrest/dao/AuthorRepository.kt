package ru.otus.crudrest.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.otus.crudrest.model.Author

@Repository
interface AuthorRepository: MongoRepository<Author, String>