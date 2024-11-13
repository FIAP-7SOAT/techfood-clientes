package br.com.fiap.techfood.app.adapter.input.web.client

import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientRequest
import br.com.fiap.techfood.app.adapter.input.web.client.dto.ClientResponse
import br.com.fiap.techfood.app.adapter.input.web.client.mapper.toClientResponse
import br.com.fiap.techfood.core.port.input.ClientInputPort
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/clients")
class ClientResource(
    private val clientInput: ClientInputPort
) {

    @PostMapping
    fun create(@RequestBody request: ClientRequest): ClientResponse {
        val client = clientInput.create(UUID.randomUUID(),request.cpf, request.name, request.email)
        return client.toClientResponse()
    }

    @GetMapping("/cpf/{cpf}")
    fun findByCpf(@PathVariable cpf: String): ClientResponse {
        val client = clientInput.getClientByCpf(cpf)
        return client.toClientResponse()
    }

    @GetMapping
    fun findAll(): List<ClientResponse> {
        val clients = clientInput.findAll()
        return clients.map { it.toClientResponse() }
    }

    @PutMapping("/{clientId}")
    fun updateClient(@PathVariable clientId: UUID, @RequestBody request: ClientRequest): ClientResponse {
        val updatedClient = clientInput.update(clientId, request.cpf, request.name, request.email)
        return updatedClient.toClientResponse()
    }

    @DeleteMapping("/{cpf}")
    fun deleteClient(@PathVariable cpf: String) {
        clientInput.delete(cpf)
    }

}
