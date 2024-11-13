package br.com.fiap.techfood.core.port.output

import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import java.util.*

interface ClientOutputPort {
    fun persist(client: Client): Client
    fun findClientByCpf(clientCPF: String): Client?
    fun findClientById(clientId: UUID): Client?
    fun findByCpf(clientCPF: String): ClientVO?
    fun findAll(): List<Client>
    fun delete(client: Client)
}