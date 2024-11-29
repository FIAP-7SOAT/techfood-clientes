package br.com.fiap.techfood.app.adapter.input.web.client

import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientRequest
import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientResponse
import br.com.fiap.techfood.app.adapter.input.web.client.mapper.toClientResponse
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.port.input.ClientInputPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/clients")
class ClientResource(
    private val clientInput: ClientInputPort
) {

    @PostMapping
    fun create(@RequestBody request: ClientRequest): ResponseEntity<Any> {
        return try {
            val client = clientInput.createClient(UUID.randomUUID(), request.cpf, request.name, request.email)
            val clientResponse = client.toClientResponse()
            ResponseEntity.status(HttpStatus.CREATED).body(clientResponse)
        } catch (e: ClientAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: UUID): Client? {
        return clientInput.getClientById(id)
    }

    @GetMapping("/cpf/{cpf}")
    fun getClientByCpf(@PathVariable cpf: String): Client? {
        return clientInput.getClientByCpf(cpf)
    }

    @GetMapping
    fun getAllClients(): List<Client> {
        return clientInput.getAllClients()
    }

    @PutMapping("/{clientId}")
    fun updateClient(@PathVariable clientId: UUID, @RequestBody request: ClientRequest): ClientResponse {
        val client = clientInput.update(clientId, request.cpf, request.name, request.email)
        return client.toClientResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteClient(@PathVariable id: UUID) {
        clientInput.deleteClient(id)
    }

}
