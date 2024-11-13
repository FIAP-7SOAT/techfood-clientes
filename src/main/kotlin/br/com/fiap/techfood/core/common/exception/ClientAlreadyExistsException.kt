package br.com.fiap.techfood.core.common.exception

open class ClientAlreadyExistsException : RuntimeException {
    companion object {
        const val MESSAGE: String = "Client already exists"
    }

    constructor(ex: Exception?) : super(MESSAGE, ex)
    constructor() : super(MESSAGE)
}
