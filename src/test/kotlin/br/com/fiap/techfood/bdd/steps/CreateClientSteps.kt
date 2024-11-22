package br.com.fiap.techfood.bdd.steps

import br.com.fiap.techfood.app.adapter.output.persistence.repository.ClientRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [br.com.fiap.techfood.app.TechfoodApplication::class])
@ActiveProfiles("test")
class CreateClientSteps {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var response: ResponseEntity<String>
//    private lateinit var clientRequest: Map<String, Any>
    private var clientRequest: Map<String, Any> = emptyMap()


    private lateinit var expectedCpf: String
    private lateinit var expectedEmail: String
    @Autowired
    private lateinit var restTemplate: RestTemplate
    @Autowired
    private lateinit var clientRepository: ClientRepository

    val url = "http://localhost:$port/api/clients"

    @BeforeEach
    fun clearDatabase() {
        // Limpar registros antigos para evitar conflitos
        clientRepository.deleteAll()
    }

    @Given("a client with CPF {string}, name {string}, and email {string}")
    fun `prepare client data`(cpf: String, name: String, email: String) {
        println("Preparing client data with CPF: $cpf, name: $name, email: $email")
        expectedCpf = "$cpf-${UUID.randomUUID()}"
        expectedEmail = "$email-${UUID.randomUUID()}@example.com"
        clientRequest = mapOf("cpf" to expectedCpf, "name" to name, "email" to expectedEmail)
    }



    @Given("a client already exists with CPF {string}")
    fun `create existing client`(cpf: String) {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val entity = HttpEntity(mapOf("cpf" to cpf, "name" to "Test User", "email" to "test@example.com"), headers)

        // Call the API or DB to check if the client exists
        val existingClientResponse = restTemplate.exchange("http://localhost:$port/api/clients", HttpMethod.GET, entity, String::class.java)

        // If client does not exist, create it, otherwise handle the conflict
        if (existingClientResponse.statusCodeValue != 200) {
            restTemplate.exchange("http://localhost:$port/api/clients", HttpMethod.POST, entity, String::class.java)
        }
    }


    @When("the client sends a POST request to {string}")
    fun `send post request`(endpoint: String) {
        println("Sending POST request with clientRequest: $clientRequest")

        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val entity = HttpEntity(clientRequest, headers)

        try {
            response = restTemplate.exchange("http://localhost:$port$endpoint", HttpMethod.POST, entity, String::class.java)
        } catch (ex: HttpServerErrorException.InternalServerError) {
            response = ResponseEntity.status(500).body(ex.responseBodyAsString)
        }
    }




    @Then("the response status should be {int}")
    fun `verify response status`(statusCode: Int) {
        println("Response Status Code: ${response.statusCodeValue}")
        assertThat(response.statusCodeValue).isEqualTo(statusCode)
    }

    @Then("the response body should contain the CPF {string}, name {string}, and email {string}")
    fun `verify response body`(cpf: String, name: String, email: String) {
        val responseBody = response.body
        assertThat(responseBody).contains("\"cpf\":\"$expectedCpf\"")
        assertThat(responseBody).contains("\"name\":\"$name\"")
        assertThat(responseBody).contains("\"email\":\"$expectedEmail\"")
    }


    @Then("the response body should contain an error message {string}")
    fun `verify error message`(errorMessage: String) {
        assertThat(response.body).contains(errorMessage)
    }

}
