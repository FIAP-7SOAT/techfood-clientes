package br.com.fiap.techfood.bdd.config

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [br.com.fiap.techfood.app.TechfoodApplication::class])
@ActiveProfiles("test")
class CucumberConfig {
    // A configuração para conectar ao contexto de Spring
}