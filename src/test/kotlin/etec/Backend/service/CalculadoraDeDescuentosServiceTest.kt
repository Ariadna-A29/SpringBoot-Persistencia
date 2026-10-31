package etec.Backend.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CalculadoraDeDescuentosServiceTest : BehaviorSpec({

    val calculadora = CalculadoraDeDescuentosService()

    Given("la calculadora de descuentos") {

        When("el monto es negativo") {

            Then("debe lanzar una excepción") {
                val excepcion =
                    shouldThrow<PrecondicionInvalidaException> {
                        calculadora.aplicarDescuentosSegunUsuario(
                            userID = "123",
                            monto = -50.0
                        )
                    }

                excepcion.message shouldBe
                    "El monto debe ser mayor a cero"
            }
        }

        When("el monto es cero") {

            Then("también debe lanzar una excepción") {
                shouldThrow<PrecondicionInvalidaException> {
                    calculadora.aplicarDescuentosSegunUsuario(
                        userID = "123",
                        monto = 0.0
                    )
                }
            }
        }

        When("el usuario 123 compra por 100") {

            Then("se aplica el descuento del 25 por ciento") {
                calculadora.aplicarDescuentosSegunUsuario(
                    userID = "123",
                    monto = 100.0
                ) shouldBe 75.0
            }
        }
    }
})
