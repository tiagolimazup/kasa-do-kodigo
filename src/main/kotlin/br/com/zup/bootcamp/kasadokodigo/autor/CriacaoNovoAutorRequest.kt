package br.com.zup.bootcamp.kasadokodigo.autor

data class CriacaoNovoAutorRequest(
        val nome: String?,
        val email: String?,
        val descricao: String?) {

    fun toModel() = Autor(nome = this.nome!!,
                                email = this.email!!,
                                descricao = this.descricao!!)

}
