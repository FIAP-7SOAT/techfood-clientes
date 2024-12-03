package br.com.fiap.techfood.core.port.input

import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import java.util.UUID

interface ClientInputPort {
    fun createClient(uuid: UUID, clientCpf: String, clientName: String, clientEmail: String): Client
    fun getClientById(id: UUID): Client?
    fun getClientByCpf(cpf: String): Client?
    fun getAllClients(): List<Client>
    fun update(clientId: UUID, clientCpf: String, clientName: String, clientEmail: String): Client
    fun deleteClient(id: UUID)

}