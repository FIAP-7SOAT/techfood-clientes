package br.com.fiap.techfood.app.adapter.output.persistence.repository

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository : JpaRepository<ClientEntity, UUID> {
    fun findClientByCpf(clientCpf: String): ClientEntity?
    fun findByCpf(cpf: String): ClientEntity?
}