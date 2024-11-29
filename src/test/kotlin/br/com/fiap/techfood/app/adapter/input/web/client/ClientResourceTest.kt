package br.com.fiap.techfood.app.adapter.input.web.client

import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientRequest
import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientResponse
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.port.input.ClientInputPort
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class ClientResourceTest {

    private val clientInput: ClientInputPort = mockk()
    private val clientResource = ClientResource(clientInput)

    @Test
    fun `create should return 201 when client is created successfully`() {
        // Arrange
        val clientRequest = ClientRequest("12345678901", "Test Client", "test@example.com")
        val client = Client(UUID.randomUUID(), clientRequest.cpf, clientRequest.name, clientRequest.email)
        every { clientInput.createClient(any(), any(), any(), any()) } returns client

        // Act
        val response = clientResource.create(clientRequest)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertTrue(response.body is ClientResponse)
        assertEquals(clientRequest.cpf, (response.body as ClientResponse).cpf)
    }

    @Test
    fun `create should return 409 when client already exists`() {
        // Arrange
        val clientRequest = ClientRequest("12345678901", "Test Client", "test@example.com")
        every { clientInput.createClient(any(), any(), any(), any()) } throws ClientAlreadyExistsException("Client already exists")

        // Act
        val response = clientResource.create(clientRequest)

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertNotNull(response.body)
        assertTrue((response.body as Map<*, *>)["error"].toString().contains("Client already exists"))
    }

    @Test
    fun `getClientById should return a client when found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val client = Client(clientId, "12345678901", "Test Client", "test@example.com")
        every { clientInput.getClientById(clientId) } returns client

        // Act
        val result = clientResource.getClientById(clientId)

        // Assert
        assertNotNull(result)
        assertEquals(clientId, result?.id)
    }

    @Test
    fun `getClientById should return null when client is not found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { clientInput.getClientById(clientId) } returns null

        // Act
        val result = clientResource.getClientById(clientId)

        // Assert
        assertNull(result)
    }

    @Test
    fun `getClientByCpf should return a client when found`() {
        // Arrange
        val cpf = "12345678901"
        val client = Client(UUID.randomUUID(), cpf, "Test Client", "test@example.com")
        every { clientInput.getClientByCpf(cpf) } returns client

        // Act
        val result = clientResource.getClientByCpf(cpf)

        // Assert
        assertNotNull(result)
        assertEquals(cpf, result?.cpf)
    }

    @Test
    fun `getClientByCpf should return null when client is not found`() {
        // Arrange
        val cpf = "12345678901"
        every { clientInput.getClientByCpf(cpf) } returns null

        // Act
        val result = clientResource.getClientByCpf(cpf)

        // Assert
        assertNull(result)
    }

    @Test
    fun `getAllClients should return a list of clients`() {
        // Arrange
        val clients = listOf(
            Client(UUID.randomUUID(), "12345678901", "Client A", "a@example.com"),
            Client(UUID.randomUUID(), "98765432100", "Client B", "b@example.com")
        )
        every { clientInput.getAllClients() } returns clients

        // Act
        val result = clientResource.getAllClients()

        // Assert
        assertEquals(2, result.size)
    }

    @Test
    fun `updateClient should return updated client details`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val clientRequest = ClientRequest("12345678901", "Updated Client", "updated@example.com")
        val updatedClient = Client(clientId, clientRequest.cpf, clientRequest.name, clientRequest.email)
        every { clientInput.update(clientId, clientRequest.cpf, clientRequest.name, clientRequest.email) } returns updatedClient

        // Act
        val result = clientResource.updateClient(clientId, clientRequest)

        // Assert
        assertNotNull(result)
        assertEquals(clientId, result.id)
        assertEquals("Updated Client", result.name)
    }

    @Test
    fun `deleteClient should not throw any exception`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { clientInput.deleteClient(clientId) } returns Unit

        // Act & Assert
        assertDoesNotThrow { clientResource.deleteClient(clientId) }
    }
}
