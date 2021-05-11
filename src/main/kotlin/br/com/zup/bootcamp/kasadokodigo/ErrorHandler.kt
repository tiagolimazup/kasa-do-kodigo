package br.com.zup.bootcamp.kasadokodigo

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun onMethodArgumentException(e: MethodArgumentNotValidException): ResponseEntity<ListOfErrors> {
        val errors = e.bindingResult.globalErrors
                                            .map { mapOf("field" to it.objectName, "message" to it.defaultMessage) } +
                                           e.bindingResult.fieldErrors
                                            .map { mapOf("field" to it.field, "message" to it.defaultMessage) }

        return ResponseEntity.badRequest()
                .body(mapOf("errors" to errors))
    }
}

typealias ListOfErrors = Map<String, List<Map<String, String?>>>