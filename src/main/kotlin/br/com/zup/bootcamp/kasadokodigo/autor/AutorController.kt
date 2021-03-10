package br.com.zup.bootcamp.kasadokodigo.autor

import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager

@RestController
@RequestMapping("/autor")
class AutorController(val entityManager: EntityManager) {

    @PostMapping
    @Transactional
    fun criaNovoAutor(@RequestBody criacaoNovoAutorRequest: CriacaoNovoAutorRequest) {
        criacaoNovoAutorRequest.toModel()
                .also { entityManager.persist(it) }
                .let { ResponseEntity.ok().build<Unit>() }
    }
}