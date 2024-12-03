package br.com.fiap.techfood.core.usecase.client

import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.port.output.ClientOutputPort
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ClientUseCaseTest {

    private val clientOutputPort: ClientOutputPort = mockk()
    private val clientUseCase = ClientUseCase(clientOutputPort)

    @Test
    fun `createClient should save a new client`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val clientCpf = "12345678901"
        val clientName = "Client A"
        val clientEmail = "clientA@example.com"
        val client = Client(clientId, clientCpf, clientName, clientEmail)
        every { clientOutputPort.findByCpf(clientCpf) } returns null
        every { clientOutputPort.save(client) } returns client

        // Act
        val result = clientUseCase.createClient(clientId, clientCpf, clientName, clientEmail)

        // Assert
        assertNotNull(result)
        assertEquals(clientId, result.id)
        assertEquals(clientName, result.name)
        verify { clientOutputPort.findByCpf(clientCpf) }
        verify { clientOutputPort.save(client) }
    }

    @Test
    fun `createClient should throw exception if client with CPF already exists`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val clientCpf = "12345678901"
        val clientName = "Client A"
        val clientEmail = "clientA@example.com"
        val existingClient = Client(clientId, clientCpf, clientName, clientEmail)
        every { clientOutputPort.findByCpf(clientCpf) } returns existingClient

        // Act & Assert
        val exception = assertThrows(ClientAlreadyExistsException::class.java) {
            clientUseCase.createClient(clientId, clientCpf, clientName, clientEmail)
        }
        assertEquals("Client with id $clientId already exists.", exception.message)
        verify { clientOutputPort.findByCpf(clientCpf) }
        verify(exactly = 0) { clientOutputPort.save(any()) }
    }

    @Test
    fun `getClientById should return a client when found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val client = Client(clientId, "12345678901", "Client A", "clientA@example.com")
        every { clientOutputPort.findById(clientId) } returns client

        // Act
        val result = clientUseCase.getClientById(clientId)

        // Assert
        assertNotNull(result)
        assertEquals(clientId, result?.id)
        verify { clientOutputPort.findById(clientId) }
    }

    @Test
    fun `getClientById should return null when client is not found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { clientOutputPort.findById(clientId) } returns null

        // Act
        val result = clientUseCase.getClientById(clientId)

        // Assert
        assertNull(result)
        verify { clientOutputPort.findById(clientId) }
    }

    @Test
    fun `update should update an existing client`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val client = Client(clientId, "12345678901", "Client A", "clientA@example.com")
        val updatedClient = client.copy(cpf = "98765432100", name = "Updated Client", email = "updated@example.com")
        every { clientOutputPort.findById(clientId) } returns client
        every { clientOutputPort.update(clientId, updatedClient) } returns updatedClient

        // Act
        val result = clientUseCase.update(clientId, updatedClient.cpf, updatedClient.name, updatedClient.email)

        // Assert
        assertNotNull(result)
        assertEquals("Updated Client", result.name)
        verify { clientOutputPort.findById(clientId) }
        verify { clientOutputPort.update(clientId, updatedClient) }
    }

    @Test
    fun `update should throw exception if client not found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { clientOutputPort.findById(clientId) } returns null

        // Act & Assert
        val exception = assertThrows(ClientNotFoundException::class.java) {
            clientUseCase.update(clientId, "98765432100", "Updated Client", "updated@example.com")
        }
        assertEquals("Client with id $clientId not found.", exception.message)
        verify { clientOutputPort.findById(clientId) }
        verify(exactly = 0) { clientOutputPort.update(any(), any()) }
    }

    @Test
    fun `deleteClient should delete a client`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { clientOutputPort.deleteById(clientId) } just Runs

        // Act
        assertDoesNotThrow { clientUseCase.deleteClient(clientId) }

        // Assert
        verify { clientOutputPort.deleteById(clientId) }
    }

    @Test
    fun `getAllClients should return a list of clients`() {
        // Arrange
        val clients = listOf(
            Client(UUID.randomUUID(), "12345678901", "Client A", "clientA@example.com"),
            Client(UUID.randomUUID(), "98765432100", "Client B", "clientB@example.com")
        )
        every { clientOutputPort.findAll() } returns clients

        // Act
        val result = clientUseCase.getAllClients()

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size)
        verify { clientOutputPort.findAll() }
    }
}
