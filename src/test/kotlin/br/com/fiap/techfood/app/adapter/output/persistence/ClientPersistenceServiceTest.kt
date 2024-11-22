package br.com.fiap.techfood.app.adapter.output.persistence

import br.com.fiap.techfood.app.adapter.output.persistence.repository.ClientRepository
import br.com.fiap.techfood.core.domain.Client
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class ClientPersistenceServiceTest {

    private val clientRepository: ClientRepository = mockk()
    private val clientPersistenceService = ClientPersistenceService(clientRepository)

    @Test
    fun `should persist client successfully`() {
        val client = Client(UUID.randomUUID(), "12345678900", "John Doe", "john.doe@example.com")
        every { clientRepository.save(any()) } answers { firstArg() }

        val persistedClient = clientPersistenceService.persist(client)

        assertEquals(client.cpf, persistedClient.cpf)
    }
}
