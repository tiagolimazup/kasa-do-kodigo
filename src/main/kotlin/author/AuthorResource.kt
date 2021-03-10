package br.com.zup.bootcamp.kasadokodigo.author

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/author")
class AuthorResource(val entityManager: EntityManager) {

    @PostMapping
    @Transactional
    fun create(@Valid @RequestBody createNewAuthorRequest: CreateNewAuthorRequest) =
            createNewAuthorRequest.toModel()
                    .also(entityManager::persist)
                    .let{ ResponseEntity.ok().build<Unit>() }

}