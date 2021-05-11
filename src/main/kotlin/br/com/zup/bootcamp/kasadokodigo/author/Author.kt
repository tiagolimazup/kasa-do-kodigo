package br.com.zup.bootcamp.kasadokodigo.author

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Author(var email: String,
             var name: String,
             var description: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object Bla {

        fun bla() = ""
    }
}
