package br.com.fiap.techfood.app.configuration

import org.bson.types.ObjectId
import org.springframework.core.convert.converter.Converter
import java.util.*

class ObjectIdToUuidConverter : Converter<ObjectId, UUID> {
    override fun convert(source: ObjectId): UUID {
        return UUID.nameUUIDFromBytes(source.toHexString().toByteArray())
    }
}