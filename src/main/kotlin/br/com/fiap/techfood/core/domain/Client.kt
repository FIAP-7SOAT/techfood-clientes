package br.com.fiap.techfood.core.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("clients")
data class Client(
    @Id
    val id: UUID,
    val cpf: String,  // CPF do cliente
    val name: String, // Nome do cliente
    val email: String // Email do cliente
)
