package br.com.fiap.techfood.app.adapter.output.persistence

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientDocument
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toClientDocument
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toDomain
import br.com.fiap.techfood.app.adapter.output.persistence.repository.ClientRepository
import br.com.fiap.techfood.core.common.annotation.PersistenceAdapter
import br.com.fiap.techfood.core.common.exception.ClientAlreadyExistsException
import br.com.fiap.techfood.core.common.exception.ClientNotFoundException
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.port.output.ClientOutputPort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.*


@PersistenceAdapter
class ClientPersistenceService(
    private val clientRepository: ClientRepository,
    private val mongoTemplate: MongoTemplate
) : ClientOutputPort {

    override fun save(client: Client): Client {
        // Verifique se o cliente já existe no banco de dados com o mesmo ID
        val existingClient = mongoTemplate.findById(client.id, ClientDocument::class.java)

        return if (existingClient != null) {
            // Se o cliente já existir, lance uma exceção
            throw ClientAlreadyExistsException("Client with id ${client.id} already exists.")
        } else {
            // Caso contrário, crie um novo cliente
            val newClientDocument = client.toClientDocument()
            // Insira o novo cliente no MongoDB
            mongoTemplate.save(newClientDocument)
            // Converta o documento inserido para a classe de domínio e retorne
            newClientDocument.toDomain()
        }
    }

    override fun findById(id: UUID): Client? {
        // Use o MongoTemplate para buscar o documento diretamente pelo UUID
        return mongoTemplate.findById(id, ClientDocument::class.java)?.toDomain()
    }

    override fun findByCpf(cpf: String): Client? {
        val query = Query(Criteria.where("cpf").`is`(cpf))
        return mongoTemplate.findOne(query, ClientDocument::class.java)?.toDomain()
    }

    override fun findAll(): List<Client> {
        return mongoTemplate.findAll(Client::class.java)
    }

    override fun update(id: UUID, client: Client): Client {
        // Find the existing client by its UUID
        val existingClient = mongoTemplate.findById(id, ClientDocument::class.java)
            ?: throw ClientNotFoundException("Client with id $id not found.")

        // Update the existing client with new values
        val updatedClient = existingClient.copy(
            cpf = client.cpf,
            name = client.name,
            email = client.email
        )

        // Save the updated client document
        mongoTemplate.save(updatedClient)

        // Return the updated client as a domain object
        return updatedClient.toDomain()
    }


    override fun deleteById(id: UUID) {
        val query = Query(Criteria.where("_id").`is`(id))
        mongoTemplate.remove(query, ClientDocument::class.java)
    }

}