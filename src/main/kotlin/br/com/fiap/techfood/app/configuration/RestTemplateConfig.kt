package br.com.fiap.techfood.app.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ComponentScan(basePackages = ["br.com.fiap.techfood.app.configuration"])  // Garantir que a configuração seja detectada
class RestTemplateConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        val restTemplate = builder.build()
        println("RestTemplate Bean created: $restTemplate")  // Verifique se o RestTemplate é criado corretamente
        return restTemplate
    }
}