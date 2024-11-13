package br.com.fiap.techfood.core.usecase.client

import br.com.fiap.techfood.core.common.annotation.UseCase
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.common.exception.InvalidClientIdException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import br.com.fiap.techfood.core.port.input.ClientInputPort
import br.com.fiap.techfood.core.port.output.ClientOutputPort
import java.util.*

@UseCase
class ClientUseCase(
    private val clientOutput: ClientOutputPort
): ClientInputPort {
    override fun create(uuid: UUID, clientCpf: String, clientName: String, clientEmail: String): Client {
        val existingClient = clientOutput.findClientByCpf(clientCpf)
        if (existingClient != null) {
            throw ClientAlreadyExistsException()
        }

        val newClient = Client(
            id = UUID.randomUUID(),
            cpf = clientCpf,
            name = clientName,
            email = clientEmail
        )

        return clientOutput.persist(newClient)
    }

    override fun getClientByCpf(clientCpf: String): ClientVO =
        clientOutput.findByCpf(clientCpf)?: throw ClientNotFoundException()

    override fun findAll(): List<Client> {
        return clientOutput.findAll()
    }

    override fun update(clientId: UUID, clientCpf: String, clientName: String, clientEmail: String): Client {
        val existingClient = clientOutput.findClientById(clientId) ?: throw InvalidClientIdException()
        val updatedClient = existingClient.copy(cpf = clientCpf, name = clientName, email = clientEmail)
        return clientOutput.persist(updatedClient)
    }

    override fun delete(clientCpf: String) {
        val existingClient = clientOutput.findClientByCpf(clientCpf) ?: throw ClientNotFoundException()
        clientOutput.delete(existingClient)
    }

}