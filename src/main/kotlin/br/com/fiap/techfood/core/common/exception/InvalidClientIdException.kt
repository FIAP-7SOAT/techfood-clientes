package br.com.fiap.techfood.core.common.exception

open class InvalidClientIdException : RuntimeException {
    companion object {
        const val MESSAGE: String = "Invalid client id"
    }

    constructor(ex: Exception?) : super(MESSAGE, ex)
    constructor() : super(MESSAGE)
}