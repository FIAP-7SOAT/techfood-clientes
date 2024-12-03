package br.com.fiap.techfood.app.configuration

import org.bson.types.Binary
import org.springframework.core.convert.converter.Converter
import java.nio.ByteBuffer
import java.util.*

class BinaryToUUIDConverter : Converter<Binary, UUID> {
    override fun convert(source: Binary): UUID {
        val bb = ByteBuffer.wrap(source.data)
        val mostSigBits = bb.long
        val leastSigBits = bb.long
        return UUID(mostSigBits, leastSigBits)
    }
}