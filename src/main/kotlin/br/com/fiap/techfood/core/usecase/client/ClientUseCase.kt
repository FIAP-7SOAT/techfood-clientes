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

    override fun createClient(uuid: UUID, clientCpf: String, clientName: String, clientEmail: String): Client {
        val existingClient = clientOutput.findByCpf(clientCpf)
        if (existingClient != null) {
            throw ClientAlreadyExistsException("Client with id ${existingClient.id} already exists.")
        }

        val newClient = Client(
            id = uuid, // Use the provided UUID
            cpf = clientCpf,
            name = clientName,
            email = clientEmail
        )

        // Persist and return the new client
        return clientOutput.save(newClient)
    }

    override fun getClientById(id: UUID): Client? {
        return clientOutput.findById(id)
    }

    override fun getClientByCpf(cpf: String): Client {
        return clientOutput.findByCpf(cpf)
            ?: throw ClientNotFoundException("Client with CPF $cpf not found.")
    }

    override fun getAllClients(): List<Client> {
        return clientOutput.findAll()
    }

    override fun update(clientId: UUID, clientCpf: String, clientName: String, clientEmail: String): Client {
        val existingClient = clientOutput.findById(clientId)
            ?: throw ClientNotFoundException("Client with id $clientId not found.")

        // Update the client fields
        val updatedClient = existingClient.copy(
            cpf = clientCpf,
            name = clientName,
            email = clientEmail
        )

        // Return the updated client after saving
        return clientOutput.update(clientId, updatedClient)
    }


    override fun deleteClient(id: UUID) {
        clientOutput.deleteById(id)
    }

}