package br.com.fiap.techfood.bdd.steps

import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.usecase.client.ClientUseCase
import br.com.fiap.techfood.core.port.output.ClientOutputPort
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import java.util.*

class ClientStepsTest {

    private val clientOutput: ClientOutputPort = mockk()
    private val clientUseCase = ClientUseCase(clientOutput)

    private lateinit var response: ResponseEntity<Any>
    private lateinit var client: Client

    // Scenario 1: Successfully create a client
    @Given("a client with CPF {string}, name {string}, and email {string}")
    fun givenAClientWithCpfAndName(cpf: String, name: String, email: String) {
        client = Client(UUID.randomUUID(), cpf, name, email)
    }

    @When("the client sends a POST request to {string}")
    fun whenTheClientSendsRequest(url: String) {
        every { clientOutput.findByCpf(client.cpf) } returns null // Simulating no existing client

        response = try {
            every { clientOutput.save(client) } returns client
            val createdClient = clientUseCase.createClient(client.id, client.cpf, client.name, client.email)
            val location = URI.create("/api/clients/${createdClient.id}")
            ResponseEntity.created(location).body(createdClient)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Client already exists")
        }
    }

    // Scenario 2: Fail to create a client with an existing CPF
    @Given("a client already exists with CPF {string}")
    fun givenAClientAlreadyExistsWithCpf(cpf: String) {
        client = Client(UUID.randomUUID(), cpf, "Jane Doe", "jane.doe@example.com")
        every { clientOutput.findByCpf(cpf) } returns client // Simulating that the client exists
    }

    @When("the client sends a POST request to {string} with existing CPF")
    fun whenTheClientSendsRequestWithExistingCpf(url: String) {
        response = try {
            val existingClient = clientOutput.findByCpf(client.cpf)
            if (existingClient != null) {
                ResponseEntity.status(HttpStatus.CONFLICT).body("Client already exists")
            } else {
                every { clientOutput.save(client) } returns client
                val createdClient = clientUseCase.createClient(client.id, client.cpf, client.name, client.email)
                val location = URI.create("/api/clients/${createdClient.id}")
                ResponseEntity.created(location).body(createdClient)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Client already exists")
        }
    }

    // Step: Verify the response status
    @Then("the response status should be {int}")
    fun theResponseStatusShouldBe(statusCode: Int) {
        assertEquals(HttpStatus.valueOf(statusCode), response.statusCode)
    }

    // Step: Verify the response body contains CPF, name, and email
    @Then("the response body should contain the CPF {string}, name {string}, and email {string}")
    fun theResponseBodyShouldContainCpfNameAndEmail(cpf: String, name: String, email: String) {
        val responseBody = response.body as Client
        assertEquals(cpf, responseBody.cpf)
        assertEquals(name, responseBody.name)
        assertEquals(email, responseBody.email)
    }

    // Step: Verify the response body contains an error message
    @Then("the response body should contain an error message {string}")
    fun theResponseBodyShouldContainErrorMessage(errorMessage: String) {
        val responseBody = response.body as String
        assertEquals(errorMessage, responseBody)
    }
}
