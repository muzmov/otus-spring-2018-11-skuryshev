package ru.otus.crudrest.service

import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.GrantedAuthoritySid
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.crudrest.dao.AuthorRepository
import ru.otus.crudrest.exception.NotFoundException
import ru.otus.crudrest.model.Author

@Service
@Transactional
class AuthorService(
    val authorRepository: AuthorRepository,
    val aclService: MutableAclService
) {

    @PostFilter("hasPermission(filterObject, 'READ')")
    fun findAll(): Iterable<Author> {
        return authorRepository.findAll()
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    fun findById(id: Long): Author {
        return authorRepository.findById(id).orElseThrow { NotFoundException("user not found") }
    }

    @PreAuthorize("hasPermission(#author, 'WRITE')")
    fun update(author: Author): Author = authorRepository.save(author)

    fun create(author: Author): Author {
        val result = authorRepository.save(author.copy(id = null))
        val owner = PrincipalSid(SecurityContextHolder.getContext().authentication)
        val admin = GrantedAuthoritySid("ROLE_ADMIN")
        val oid = ObjectIdentityImpl(Author::class.java, result.id)
        val acl = aclService.createAcl(oid)
        acl.owner = owner
        acl.insertAce(acl.entries.size, BasePermission.READ, owner, true)
        acl.insertAce(acl.entries.size, BasePermission.WRITE, owner, true)
        acl.insertAce(acl.entries.size, BasePermission.ADMINISTRATION, owner, true)
        acl.insertAce(acl.entries.size, BasePermission.READ, admin, true)
        acl.insertAce(acl.entries.size, BasePermission.WRITE, admin, true)
        acl.insertAce(acl.entries.size, BasePermission.DELETE, admin, true)
        acl.insertAce(acl.entries.size, BasePermission.ADMINISTRATION, admin, true)
        aclService.updateAcl(acl)
        return result
    }

    @PreAuthorize("hasPermission(#author, 'DELETE')")
    fun delete(author: Author) = authorRepository.delete(author)
}