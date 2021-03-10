package br.com.zup.bootcamp.kasadokodigo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KasaDoKodigoApplication

fun main(args: Array<String>) {
    runApplication<KasaDoKodigoApplication>(*args)
}