package br.com.fiap.techfood.core.common.exception

open class ClientNotFoundException : RuntimeException {
    companion object {
        const val MESSAGE: String = "Client not found"
    }

    constructor(ex: Exception?) : super(MESSAGE, ex)
    constructor() : super(MESSAGE)
}