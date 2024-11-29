package br.com.fiap.techfood.core.port.output

import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO
import java.util.*

interface ClientOutputPort {
    fun save(client: Client): Client
    fun findById(id: UUID): Client?
    fun findByCpf(cpf: String): Client?
    fun findAll(): List<Client>
    fun update(id: UUID, client: Client): Client
    fun deleteById(id: UUID)

}