package br.com.zup.bootcamp.kasadokodigo.author

import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager
import javax.validation.Valid

@RestController
@RequestMapping("/author")
class AuthorResource(val entityManager: EntityManager) {

    @PostMapping
    @Transactional
    fun create(@Valid @RequestBody request: CreateNewAuthorRequest): ResponseEntity<Unit> {
        entityManager.persist(request.toAuthor())
        return ResponseEntity.ok().build()
    }
}