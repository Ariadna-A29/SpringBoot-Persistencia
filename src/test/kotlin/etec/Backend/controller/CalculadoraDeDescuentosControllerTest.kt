package etec.Backend.controller

import com.ninjasquad.springmockk.MockkBean
import etec.Backend.service.CalculadoraDeDescuentosService
import etec.Backend.service.PrecondicionInvalidaException
//import io.kotest.core.annotation.ApplyExtension
import io.kotest.core.extensions.ApplyExtension

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApplyExtension(SpringExtension::class) // Habilita la inyección por constructor en Kotest
@WebMvcTest(CalculadoraDeDescuentosController::class)
class CalculadoraDeDescuentosControllerTest(
    private val navegador: MockMvc,
    @MockkBean private val calculadora: CalculadoraDeDescuentosService
) : BehaviorSpec({

    Given("un endpoint para calcular descuentos /api/descuentos") {
				// Otros tests
        And("un usuario 123 quiere aplicar descuentos a un monto menor a 0") {

            // Configuramos el Mock para que simule lanzar la excepción de negocio
            every {
                calculadora.aplicarDescuentosSegunUsuario("123", -10.0)
            } throws PrecondicionInvalidaException("El monto debe ser mayor a cero")

            When("se hace la petición con el monto inválido") {
                val respuesta = navegador.perform(
                    get("/api/descuentos")
                        .param("userID", "123")
                        .param("monto", "-10.0")
                )

                Then("el controlador debe traducir la excepción a un estado HTTP 400 Bad Request") {
                    respuesta.andExpect(status().isBadRequest)
                }

                And("debe devolver un formato JSON estructurado con el mensaje de error") {
                    respuesta.andExpect(jsonPath("$.error").value("El monto debe ser mayor a cero"))
                }
            }
        }
    }
})
