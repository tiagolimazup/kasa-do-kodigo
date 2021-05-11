package br.com.zup.bootcamp.kasadokodigo.author

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateNewAuthorRequest(
        @field:Email(message = "Deve ser um endereco de email valido.")
        @field:NotBlank(message = "O email deve estar preenchido.")
        val email: String?,

        @field:NotBlank
        val name: String?,

        @field:NotBlank
        @field:Size(max = 400)
        val description: String?) {

    fun toAuthor() = Author(email = email!!, name = name!!, description = description!!)

}