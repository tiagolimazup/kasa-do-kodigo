package br.com.zup.bootcamp.kasadokodigo.autor

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import javax.persistence.EntityManager
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class AutorControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun `deve criar um novo autor`() {
        mockMvc.post("/autor") {
            contentType = MediaType.APPLICATION_JSON
            content = CriacaoNovoAutorRequest(nome = "Tiago",
                                              email = "tiago.lima@zup.com.br",
                                              descricao = "Oi gente").toJson()
        }.andExpect {
            status { isOk() }
        }

        with (entityManager.createQuery("select a from Autor a", Autor::class.java).resultList) {
            assertAll({ assertTrue { size == 1 }},
                      { assertEquals("tiago.lima@zup.com.br", get(0).email) })
        }
    }

    fun CriacaoNovoAutorRequest.toJson() = objectMapper.writeValueAsString(this)

}