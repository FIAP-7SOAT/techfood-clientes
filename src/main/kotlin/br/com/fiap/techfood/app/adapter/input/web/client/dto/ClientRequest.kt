package br.com.fiap.techfood.app.adapter.input.web.client.dto

import org.jetbrains.annotations.NotNull

data class ClientRequest(
    @field:NotNull
    var cpf: String,
    @field:NotNull
    var name: String,
    @field:NotNull
    var email: String
)