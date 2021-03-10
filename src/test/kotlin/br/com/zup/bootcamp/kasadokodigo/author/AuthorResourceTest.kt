package br.com.zup.bootcamp.kasadokodigo.author

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
class AuthorResourceTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var jsonMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun `create a new author`() {
        val createNewAuthorRequest = CreateNewAuthorRequest(name = "Tiago de Freitas Lima",
                                                            email = "tiago.lima@zup.com.br",
                                                            description = "Ola")

        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = createNewAuthorRequest.json()
            }
        }.andExpect {
            status { isOk() }
        }

        with(entityManager.createQuery("select a from Author a", Author::class.java).resultList) {
            assertAll({ assertTrue { size == 1 }},
                      { assertTrue { get(0).email == "tiago.lima@zup.com.br" }})
        }
    }

    @Test
    fun `should not create a new author when name is empty`() {
        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = CreateNewAuthorRequest(email = "tiago.lima@zup.com.br", description = "Ola").json()
            }
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should not create a new author when email is empty`() {
        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = CreateNewAuthorRequest(name = "Tiago de Freitas Lima", description = "Ola").json()
            }
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should not create a new author when email is not valid`() {
        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = CreateNewAuthorRequest(name = "Tiago de Freitas Lima",
                                                 email = "bla",
                                                 description = "Ola").json()
            }
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should not create a new author when description is empty`() {
        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = CreateNewAuthorRequest(name = "Tiago de Freitas Lima", email = "tiago.lima@zup.com.br").json()
            }
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should not create a new author when email already exists`() {
        val author = Author(name = "Tiago de Freitas Lima", email = "tiago.lima@zup.com.br", description = "Oi")
                .also(entityManager::persist)

        mockMvc.post("/author") {
            headers {
                contentType = MediaType.APPLICATION_JSON
                content = CreateNewAuthorRequest(name = author.name,
                                                 email = author.email,
                                                 description = author.description).json()
            }
        }.andExpect {
            status { isBadRequest() }
        }
    }

    fun CreateNewAuthorRequest.json() = jsonMapper.writeValueAsString(this)
}