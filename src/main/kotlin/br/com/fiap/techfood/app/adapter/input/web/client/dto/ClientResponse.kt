package br.com.fiap.techfood.app.adapter.input.web.client.dto

import java.util.UUID

data class ClientResponse (
    var id: UUID,
    var cpf : String,
    var name: String,
    var email: String
)