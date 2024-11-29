package br.com.fiap.techfood.app.configuration

import org.bson.types.ObjectId
import org.springframework.core.convert.converter.Converter
import java.util.*

class UuidToObjectIdConverter : Converter<UUID, ObjectId> {
    override fun convert(source: UUID): ObjectId {
        return ObjectId(source.toString().replace("-", "").substring(0, 24))
    }
}