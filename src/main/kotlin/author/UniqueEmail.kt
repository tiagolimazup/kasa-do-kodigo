package br.com.zup.bootcamp.kasadokodigo.author

import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueEmailValidator::class])
annotation class UniqueEmail(
        val message: String = "{email.already.exists}",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [])

class UniqueEmailValidator(val entityManager: EntityManager) : ConstraintValidator<UniqueEmail, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext) =
        entityManager.createQuery("select 1 from Author a where email = :email")
                .setParameter("email", value)
                .resultList
                .run { size == 0 }
}
