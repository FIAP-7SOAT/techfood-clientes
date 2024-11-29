package br.com.fiap.techfood.app.adapter.output.persistence.mapper

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientDocument
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO

fun Client.toClientDocument(): ClientDocument =
    ClientDocument(
        id = this.id, // Usando diretamente o UUID do Client
        cpf = this.cpf,
        name = this.name,
        email = this.email
    )

fun ClientDocument.toDomain(): Client =
    Client(
        id = this.id, // O id já é UUID
        cpf = this.cpf,
        name = this.name,
        email = this.email
    )

fun ClientDocument.toClientVO(): ClientVO =
    ClientVO(
        id = this.id, // O id já é UUID
        cpf = this.cpf,
        name = this.name,
        email = this.email
    )
