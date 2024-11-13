package br.com.fiap.techfood.core.port.input

import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import java.util.UUID

interface ClientInputPort {
    fun create(uuid: UUID, clientCpf: String, clientName: String, clientEmail: String): Client
    fun getClientByCpf(clientCpf: String): ClientVO
    fun findAll(): List<Client>
    fun update(clientId: UUID, clientCpf: String, clientName: String, clientEmail: String): Client
    fun delete(clientCpf: String)
}