import br.com.fiap.techfood.app.adapter.input.web.client.ClientResource
import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientRequest
import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientResponse
import br.com.fiap.techfood.app.adapter.input.web.client.mapper.toClientResponse
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import br.com.fiap.techfood.core.port.input.ClientInputPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*


class ClientResourceTest {

    private val clientInput = mockk<ClientInputPort>()
    private val clientResource = ClientResource(clientInput)

    @Test
    fun `should return 201 when creating a client successfully`() {
        val clientRequest = ClientRequest("12345678901", "John Doe", "john@example.com")

        // Mock Client domain object returned by input port
        val mockClient = Client(UUID.randomUUID(), "John Doe", "john@example.com", "12345678901")

        // Mock clientInput methods
        every { clientInput.create(any(), any(), any(), any()) } returns mockClient
        every { clientInput.getClientByCpf(any()) } throws ClientNotFoundException()

        // Expected response after mapping
        val expectedResponse = mockClient.toClientResponse()

        val response = clientResource.create(clientRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(expectedResponse, response.body)
    }



    @Test
    fun `should return 409 when client already exists`() {
        val clientRequest = ClientRequest("12345678901", "John Doe", "john@example.com")

        // Mock ClientVO as returned by the service
        val mockClientVO = ClientVO(UUID.randomUUID(), "John Doe", "john@example.com", "12345678901")

        // Mock the behavior of clientInput.getClientByCpf to return ClientVO
        every { clientInput.getClientByCpf(any()) } returns mockClientVO

        // Perform the create operation
        val response = clientResource.create(clientRequest)

        // Assert that the response indicates a conflict
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertEquals(mapOf("error" to "Client already exists"), response.body)
    }








    @Test
    fun `should return 404 when client not found`() {
        val cpf = "12345678901"

        // Mocking getClientByCpf to throw ClientNotFoundException
        every { clientInput.getClientByCpf(cpf) } throws ClientNotFoundException()

        val exception = kotlin.runCatching {
            clientResource.findByCpf(cpf)
        }

        assert(exception.isFailure)
        assert(exception.exceptionOrNull() is ClientNotFoundException)
    }

    @Test
    fun `should return client when found by CPF`() {
        val cpf = "12345678901"
        val mockClient = ClientVO(UUID.randomUUID(), "John Doe", "john@example.com", cpf)

        // Mocking clientInput.getClientByCpf() to return a Client
        every { clientInput.getClientByCpf(cpf) } returns mockClient

        // Map to ClientResponse (using a mapper)
        val expectedResponse = mockClient.toClientResponse()

        val response = clientResource.findByCpf(cpf)

        assertEquals(expectedResponse, response)
    }


    @Test
    fun `should return 200 and update client`() {
        val clientId = UUID.randomUUID()
        val clientRequest = ClientRequest("12345678901", "John Doe", "john@example.com")

        // Mock domain model object (Client)
        val updatedClient = Client(clientId, "John Doe", "john@example.com", "12345678901")

        // Mocking clientInput.update() to return the domain Client
        every { clientInput.update(clientId, any(), any(), any()) } returns updatedClient

        // Perform the update operation
        val response = clientResource.updateClient(clientId, clientRequest)

        // Convert the updated domain Client to ClientResponse for assertion
        val expectedResponse = ClientResponse(clientId, "John Doe", "john@example.com", "12345678901")

        assertEquals(expectedResponse, response)
    }


    @Test
    fun `should delete client when CPF is found`() {
        val cpf = "12345678901"
        val mockClientVO = ClientVO(UUID.randomUUID(), "John Doe", "john@example.com", cpf)

        // Mocking clientInput.getClientByCpf() to return a ClientVO
        every { clientInput.getClientByCpf(cpf) } returns mockClientVO

        // Mocking clientInput.delete() to do nothing
        every { clientInput.delete(cpf) } returns Unit

        // Perform the delete operation
        clientResource.deleteClient(cpf)

        // Verify that the delete method is called
        verify { clientInput.delete(cpf) }
    }

    @Test
    fun `should create client successfully`() {
        val clientRequest = ClientRequest("12345678900", "John Doe", "john@example.com")
        val mockClient = Client(UUID.randomUUID(), "12345678900", "John Doe", "john@example.com")

        every { clientInput.getClientByCpf(clientRequest.cpf) } throws ClientNotFoundException()
        every { clientInput.create(any(), clientRequest.cpf, clientRequest.name, clientRequest.email) } returns mockClient

        val response = clientResource.create(clientRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(mockClient.toClientResponse(), response.body)
    }

    @Test
    fun `should return client by CPF`() {
        val cpf = "12345678900"
        val mockClient = ClientVO(UUID.randomUUID(), "John Doe", "john@example.com", cpf)

        every { clientInput.getClientByCpf(cpf) } returns mockClient

        val response = clientResource.findByCpf(cpf)

        assertEquals(mockClient.toClientResponse(), response)
    }


}
