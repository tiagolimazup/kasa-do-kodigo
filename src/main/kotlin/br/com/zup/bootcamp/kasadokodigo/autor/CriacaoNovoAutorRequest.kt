package br.com.zup.bootcamp.kasadokodigo.autor

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CriacaoNovoAutorRequest(
        @NotBlank
        val nome: String?,

        @NotBlank
        @Email
        val email: String?,

        @NotBlank
        @Size(max = 400)
        val descricao: String?) {

    fun toModel() = Autor(nome = this.nome!!,
                                email = this.email!!,
                                descricao = this.descricao!!)

}
