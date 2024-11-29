package br.com.fiap.techfood.app.adapter.output.persistence

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientDocument
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.domain.Client
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.*

class ClientPersistenceServiceTest {

    private val mongoTemplate: MongoTemplate = mockk()
    private val clientPersistenceService = ClientPersistenceService(mockk(), mongoTemplate)

    @Test
    fun `save should insert a new client`() {
        // Arrange
        val client = Client(UUID.randomUUID(), "12345678901", "Client A", "clientA@example.com")
        val clientDocument = ClientDocument(client.id, client.cpf, client.name, client.email)
        every { mongoTemplate.findById(client.id, ClientDocument::class.java) } returns null
        every { mongoTemplate.save(clientDocument) } returns clientDocument

        // Act
        val result = clientPersistenceService.save(client)

        // Assert
        assertNotNull(result)
        assertEquals(client.id, result.id)
        verify(exactly = 1) { mongoTemplate.save(clientDocument) }
    }

    @Test
    fun `save should throw exception if client already exists`() {
        // Arrange
        val client = Client(UUID.randomUUID(), "12345678901", "Client A", "clientA@example.com")
        val existingClientDocument = ClientDocument(client.id, client.cpf, client.name, client.email)
        every { mongoTemplate.findById(client.id, ClientDocument::class.java) } returns existingClientDocument

        // Act & Assert
        val exception = assertThrows(ClientAlreadyExistsException::class.java) {
            clientPersistenceService.save(client)
        }
        assertEquals("Client with id ${client.id} already exists.", exception.message)
        verify(exactly = 0) { mongoTemplate.save(any<ClientDocument>()) }
    }

    @Test
    fun `findById should return a client when found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val clientDocument = ClientDocument(clientId, "12345678901", "Client A", "clientA@example.com")
        every { mongoTemplate.findById(clientId, ClientDocument::class.java) } returns clientDocument

        // Act
        val result = clientPersistenceService.findById(clientId)

        // Assert
        assertNotNull(result)
        assertEquals(clientId, result?.id)
        assertEquals("12345678901", result?.cpf)
    }

    @Test
    fun `findById should return null when client is not found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { mongoTemplate.findById(clientId, ClientDocument::class.java) } returns null

        // Act
        val result = clientPersistenceService.findById(clientId)

        // Assert
        assertNull(result)
    }

    @Test
    fun `findByCpf should return a client when found`() {
        // Arrange
        val cpf = "12345678901"
        val clientDocument = ClientDocument(UUID.randomUUID(), cpf, "Client A", "clientA@example.com")
        every { mongoTemplate.findOne(any<Query>(), eq(ClientDocument::class.java)) } returns clientDocument

        // Act
        val result = clientPersistenceService.findByCpf(cpf)

        // Assert
        assertNotNull(result)
        assertEquals(cpf, result?.cpf)
    }

    @Test
    fun `findByCpf should return null when client is not found`() {
        // Arrange
        val cpf = "12345678901"
        every { mongoTemplate.findOne(any<Query>(), eq(ClientDocument::class.java)) } returns null

        // Act
        val result = clientPersistenceService.findByCpf(cpf)

        // Assert
        assertNull(result)
    }

    @Test
    fun `update should update an existing client`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val existingClientDocument = ClientDocument(clientId, "12345678901", "Client A", "clientA@example.com")
        val updatedClient = Client(clientId, "98765432100", "Updated Client", "updated@example.com")
        val updatedClientDocument = ClientDocument(updatedClient.id, updatedClient.cpf, updatedClient.name, updatedClient.email)
        every { mongoTemplate.findById(clientId, ClientDocument::class.java) } returns existingClientDocument
        every { mongoTemplate.save(updatedClientDocument) } returns updatedClientDocument

        // Act
        val result = clientPersistenceService.update(clientId, updatedClient)

        // Assert
        assertNotNull(result)
        assertEquals(updatedClient.id, result.id)
        assertEquals("Updated Client", result.name)
        verify(exactly = 1) { mongoTemplate.save(updatedClientDocument) }
    }

    @Test
    fun `update should throw exception if client not found`() {
        // Arrange
        val clientId = UUID.randomUUID()
        val updatedClient = Client(clientId, "98765432100", "Updated Client", "updated@example.com")
        every { mongoTemplate.findById(clientId, ClientDocument::class.java) } returns null

        // Act & Assert
        val exception = assertThrows(ClientNotFoundException::class.java) {
            clientPersistenceService.update(clientId, updatedClient)
        }
        assertEquals("Client with id $clientId not found.", exception.message)
        verify(exactly = 0) { mongoTemplate.save(any<ClientDocument>()) }
    }

    @Test
    fun `deleteById should remove a client`() {
        // Arrange
        val clientId = UUID.randomUUID()
        every { mongoTemplate.remove(any<Query>(), eq(ClientDocument::class.java)) } returns mockk()

        // Act
        assertDoesNotThrow { clientPersistenceService.deleteById(clientId) }

        // Assert
        verify(exactly = 1) { mongoTemplate.remove(any<Query>(), eq(ClientDocument::class.java)) }
    }
}
