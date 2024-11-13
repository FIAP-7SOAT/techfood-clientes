package br.com.fiap.techfood.core.domain

import java.util.*

data class Client(
    val id: UUID,
    var cpf : String,
    var name: String,
    var email: String,
)
