package br.com.fiap.techfood.app.adapter.input.web.handler

import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerExceptionHandler {

    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String?,
        val timestamp: Long = System.currentTimeMillis()
    )

    @ExceptionHandler(ClientNotFoundException::class)
    fun handleClientNotFoundException(ex: ClientNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ClientAlreadyExistsException::class)
    fun handleClientAlreadyExistsException(ex: ClientAlreadyExistsException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            error = "Conflict",
            message = ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message ?: "Unexpected error occurred"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
