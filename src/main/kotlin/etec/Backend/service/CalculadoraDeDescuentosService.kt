package etec.Backend.service

import org.springframework.stereotype.Service

@Service
class CalculadoraDeDescuentosService{

    fun aplicarDescuentosSegunUsuario(userID: String, monto: Double): Double {
        // 1. Verificación de regla de negocio
        if (monto <= 0) {
            // 2. Si no cumple, "lanzamos" la alarma e interrumpimos la función
            throw PrecondicionInvalidaException("El monto debe ser mayor a cero")
        }

        // 3. Si el monto es válido, la ejecución continúa hacia la lógica normal
        return monto
    }
}
