package br.com.fiap.techfood.app.adapter.input.web.advice

//import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.common.exception.InvalidClientIdException
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        log.error(ex.message, ex)
        val errors = ex.bindingResult.fieldErrors
            .map { DetailedErrorResponse(it.field, it.defaultMessage) }
            .toList()
        return buildResponse(status, "Invalid parameters", errors)
    }

    @ExceptionHandler(
        ClientNotFoundException::class,
        ClientAlreadyExistsException::class,
        InvalidClientIdException::class,
    )
    fun handleConflictExceptions(ex: RuntimeException): ResponseEntity<Any> =
        buildResponse(CONFLICT, ex.message)


    fun buildResponse(status: HttpStatusCode, message: String?): ResponseEntity<Any> = ResponseEntity
        .status(status)
        .body(CustomErrorResponse(status.value(), message, LocalDateTime.now()))

    fun buildResponse(
        status: HttpStatusCode,
        message: String?,
        errors: List<DetailedErrorResponse>?,
    ): ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .body(CustomErrorResponse(status.value(), message, LocalDateTime.now(), errors))
}

data class CustomErrorResponse(
    val code: Int,
    val message: String?,
    val timestamp: LocalDateTime,
    val errors: List<DetailedErrorResponse>? = null,
)

data class DetailedErrorResponse(
    val field: String?,
    val description: String?,
)
