package br.com.fiap.techfood.app.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MongoConfig {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var mongoUri: String

    @Value("\${spring.data.mongodb.database}")
    private val databaseName: String? = null

    @Bean
    fun mongoClient(): MongoClient {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(mongoUri))
            .uuidRepresentation(UuidRepresentation.STANDARD)  // Certifique-se de que a configuração UUID esteja aqui
            .build()
        return MongoClients.create(settings)
    }

    @Bean
    fun mongoDatabaseFactory(): MongoDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(mongoClient(), "techfood-clientes")
    }

    @Bean
    fun mongoTemplate(mongoDatabaseFactory: MongoDatabaseFactory): MongoTemplate {
        val mongoMappingContext = MongoMappingContext()
        val converter = MappingMongoConverter(mongoDatabaseFactory, mongoMappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null)) // Evita o campo _class
        converter.afterPropertiesSet()
        return MongoTemplate(mongoDatabaseFactory, converter)
    }

}
