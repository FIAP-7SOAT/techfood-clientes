package br.com.fiap.techfood.core.domain.vo

import java.util.UUID

data class ClientVO(
    val id: UUID,
    val cpf: String,
    val email: String,
    val name: String,
)