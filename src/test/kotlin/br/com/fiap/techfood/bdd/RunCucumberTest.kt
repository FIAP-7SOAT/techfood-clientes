package br.com.fiap.techfood.bdd

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/kotlin/br/com/fiap/techfood/bdd/features"],
    glue = ["br.com.fiap.techfood.bdd.steps", "br.com.fiap.techfood.bdd.config"],
    plugin = ["pretty"]
)
class RunCucumberTest
