package br.com.fiap.techfood.app.adapter.output.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("clients")
data class ClientDocument(
    @Id
    val id: UUID,
    val cpf: String,
    val name: String,
    val email: String,
)



