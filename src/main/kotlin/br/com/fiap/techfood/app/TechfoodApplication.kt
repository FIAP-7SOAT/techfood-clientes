package br.com.fiap.techfood.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["br.com.fiap.techfood"])
@ConfigurationPropertiesScan
class TechfoodApplication

fun main(args: Array<String>) {
	runApplication<TechfoodApplication>(*args)
}
