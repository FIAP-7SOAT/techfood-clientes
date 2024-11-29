package br.com.fiap.techfood.app.adapter.output.persistence.repository

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientDocument
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ClientRepository : MongoRepository<ClientDocument, UUID> {
    fun findByCpf(cpf: String): ClientDocument?
}