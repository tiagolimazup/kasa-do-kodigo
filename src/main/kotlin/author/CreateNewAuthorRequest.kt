package br.com.zup.bootcamp.kasadokodigo.author

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateNewAuthorRequest(
        @JsonProperty
        @field:NotBlank
        val name: String? = null,

        @JsonProperty
        @field:NotBlank
        @field:Email
        @field:UniqueEmail
        val email: String? = null,

        @JsonProperty
        @field:NotBlank
        @field:Size(max = 400)
        val description: String? = null) {

    fun toModel() = Author(name = name!!,
                                  email = email!!,
                                  description = description!!)
}
