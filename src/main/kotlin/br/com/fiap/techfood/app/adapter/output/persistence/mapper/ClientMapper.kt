package br.com.fiap.techfood.app.adapter.output.persistence.mapper

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ClientEntity
import br.com.fiap.techfood.core.domain.Client
import br.com.fiap.techfood.core.domain.vo.ClientVO

fun Client.toEntity(): ClientEntity =
    ClientEntity(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        email = this.email,
    )

fun ClientEntity.toDomain(): Client =
    Client(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        email = this.email,
    )

fun ClientEntity.toClientVO(): ClientVO =
    ClientVO(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        email = this.email,
    )