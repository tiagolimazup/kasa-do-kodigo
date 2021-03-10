package br.com.zup.bootcamp.kasadokodigo.author

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Author(
    @NotBlank
    @Column(nullable = false)
    val name: String,

    @NotBlank
    @Column(nullable = false, unique = true)
    val email: String,

    @NotBlank
    @Column(nullable = false, length = 400)
    val description: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
