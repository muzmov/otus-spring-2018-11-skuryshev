package ru.otus.crudrest.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.otus.crudrest.model.Role

@Repository
interface RoleRepository: CrudRepository<Role, Long>