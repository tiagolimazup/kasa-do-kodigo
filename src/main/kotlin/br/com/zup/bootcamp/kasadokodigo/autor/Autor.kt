package br.com.zup.bootcamp.kasadokodigo.autor

import org.springframework.util.Assert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class Autor(

        @NotBlank
        val nome: String,

        @NotBlank
        @Email
        val email: String,

        @NotBlank
        @Size(max = 400)
        val descricao: String) {

    init {
        Assert.hasText(nome, "Nome nao pode estar em branco ou nulo")
        Assert.hasText(email, "Email nao pode estar em branco ou nulo")
        Assert.hasText(descricao, "Descricao nao pode estar em branco ou nulo")
        Assert.state(descricao.length <= 400, "Descricao nao pode passar de 400 caracteres")
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
