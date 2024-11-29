package br.com.fiap.techfood.app.configuration

import org.bson.types.Binary
import org.springframework.core.convert.converter.Converter
import java.nio.ByteBuffer
import java.util.*

class UUIDToBinaryConverter : Converter<UUID, Binary> {
    override fun convert(source: UUID): Binary {
        val bb = ByteBuffer.wrap(ByteArray(16))
        bb.putLong(source.mostSignificantBits)
        bb.putLong(source.leastSignificantBits)
        return Binary(bb.array())
    }
}