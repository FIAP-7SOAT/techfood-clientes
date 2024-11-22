package br.com.fiap.techfood.core.usecase.client

import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.common.exception.InvalidClientIdException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import br.com.fiap.techfood.core.port.output.ClientOutputPort
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ClientUseCaseTest {

    private val clientOutput: ClientOutputPort = mockk()
    private val clientUseCase = ClientUseCase(clientOutput)

    @Test
    fun `should create a new client`() {
        val client = Client(UUID.randomUUID(), "12345678900", "John Doe", "john@example.com")

        every { clientOutput.findClientByCpf(client.cpf) } returns null
        every { clientOutput.persist(any()) } answers { firstArg() }

        val createdClient = clientUseCase.create(client.id, client.cpf, client.name, client.email)

        assertEquals(client.cpf, createdClient.cpf)
    }


    @Test
    fun `should throw exception when client already exists`() {
        val cpf = "12345678900"
        every { clientOutput.findClientByCpf(cpf) } returns Client(UUID.randomUUID(), cpf, "Existing User", "existing@example.com")

        assertThrows(ClientAlreadyExistsException::class.java) {
            clientUseCase.create(UUID.randomUUID(), cpf, "New User", "new@example.com")
        }
    }

    @Test
    fun `should get client by CPF`() {
        val clientCpf = "12345678900"
        val clientVO = ClientVO(UUID.randomUUID(), clientCpf, "John Doe", "john@example.com")

        every { clientOutput.findByCpf(clientCpf) } returns clientVO

        val result = clientUseCase.getClientByCpf(clientCpf)

        assertEquals(clientCpf, result.cpf)
    }

    @Test
    fun `should throw exception when client not found by CPF`() {
        val cpf = "12345678900"
        every { clientOutput.findByCpf(cpf) } returns null

        assertThrows(ClientNotFoundException::class.java) {
            clientUseCase.getClientByCpf(cpf)
        }
    }

    @Test
    fun `should return all clients`() {
        val clients = listOf(Client(UUID.randomUUID(), "12345678900", "John Doe", "john@example.com"))
        every { clientOutput.findAll() } returns clients

        val result = clientUseCase.findAll()

        assertFalse(result.isEmpty())
        assertEquals(1, result.size)
    }

    @Test
    fun `should update client successfully`() {
        val clientId = UUID.randomUUID()
        val updatedClient = Client(clientId, "12345678900", "John Doe Updated", "john_updated@example.com")

        every { clientOutput.findClientById(clientId) } returns updatedClient
        every { clientOutput.persist(any()) } answers { firstArg() }

        val result = clientUseCase.update(clientId, "12345678900", "John Doe Updated", "john_updated@example.com")

        assertEquals(updatedClient.id, result.id)
        assertEquals("John Doe Updated", result.name)
    }

    @Test
    fun `should throw exception when client to update not found`() {
        val clientId = UUID.randomUUID()

        every { clientOutput.findClientById(clientId) } returns null

        assertThrows(InvalidClientIdException::class.java) {
            clientUseCase.update(clientId, "12345678900", "John Doe", "john@example.com")
        }
    }


}
