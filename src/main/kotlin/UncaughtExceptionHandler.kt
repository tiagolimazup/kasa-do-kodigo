package br.com.zup.bootcamp.kasadokodigo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UncaughtExceptionHandler {

    @ExceptionHandler
    fun onMethodArgumentNotValid(ex: MethodArgumentNotValidException) =
        ResponseEntity.badRequest().body(ex.globalErrors.map { mapOf(Pair(it.objectName, it.defaultMessage)) } +
                                              ex.fieldErrors.map { mapOf(Pair(it.field, it.defaultMessage)) })

}