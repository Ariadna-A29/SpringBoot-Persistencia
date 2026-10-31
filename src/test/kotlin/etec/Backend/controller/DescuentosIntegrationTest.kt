package etec.Backend.controller

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DescuentosIntegrationTest(
    private val navegador: MockMvc
) : BehaviorSpec({

    Given("la aplicación con sus servicios reales") {

        When("se envía un monto negativo") {

            Then("debe responder HTTP 400 con el mensaje de error") {
                navegador.perform(
                    get("/api/descuentos")
                        .param("userID", "123")
                        .param("monto", "-100.0")
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(
                        jsonPath("$.error").value(
                            "El monto debe ser mayor a cero"
                        )
                    )
            }
        }

        When("se envía un monto válido de 100") {

            Then("debe devolver el monto final de 75") {
                navegador.perform(
                    get("/api/descuentos")
                        .param("userID", "123")
                        .param("monto", "100.0")
                )
                    .andExpect(status().isOk)
                    .andExpect(
                        jsonPath("$.montoFinal").value(75.0)
                    )
            }
        }
    }
})
