package br.com.zup.bootcamp.kasadokodigo.author

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
class AuthorResourceTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    val request = CreateNewAuthorRequest(email = "tiago.lima@zup.com.br", name ="Tiago", description = "Oi")

    @Test
    fun `deve criar um novo autor`() {
        mockMvc.post("/author") {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { isOk() }
        }

        val list = entityManager.createQuery("from Author where email = :email", Author::class.java)
                .setParameter("email", request.email)
                .resultList

        with (list) {
            assertTrue(size == 1)

            assertAll({ assertEquals(request.email, get(0).email) },
                      { assertEquals(request.name, get(0).name) },
                      { assertEquals(request.description, get(0).description) })
        }
    }

    @ParameterizedTest(name = "Restricao: {0}")
    @MethodSource("restrictions")
    fun `deve obeder as restricoes ao tentar criar um autor`(message: String,
                                                             invalidRequest: CreateNewAuthorRequest,
                                                             errorJsonPath: String,
                                                             expectedMessage: String){

        mockMvc.post("/author") {
            contentType = MediaType.APPLICATION_JSON
            content = invalidRequest.toJson()
        }.andExpect {
            status { isBadRequest() }
            jsonPath(errorJsonPath){
                value(expectedMessage)
            }
        }

        val list = entityManager.createQuery("from Author", Author::class.java)
                .resultList

        assertTrue(list.isEmpty())
    }

    companion object {

        @JvmStatic
        fun restrictions(): List<Arguments> {
            val request = CreateNewAuthorRequest(email = null, name = null, description = null)

            return listOf(of("Email invalido", request.copy(email = "bla@"), "\$.errors[?(@.field=='email')].message", "Deve ser um endereco de email valido."),
                          of("Email em branco", request, "\$.errors[?(@.field=='email')].message", "O email deve estar preenchido."))
        }
    }

    fun CreateNewAuthorRequest.toJson() = objectMapper.writeValueAsString(this)
}