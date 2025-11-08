package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.dto.MotorDTO
import br.com.hacka.motorprecificacao.service.MotorIaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Controlador responsável por consultar taxas e cálculos via motor.

@RestController
@RequestMapping("/api/motor")
class MotorIaController(private val motorIaService: MotorIaService) {

    @PostMapping("/taxas")
    fun consultarTaxas(@RequestBody request: MotorDTO): ResponseEntity<Any> {
        return try {
            // Chama o serviço para consultar as taxas
            val response = motorIaService.consultarTaxasIA(request)
            ResponseEntity.ok(response) // Retorna o DTO da resposta com o código 200 (OK)
        } catch (e: Exception) {
            // Em caso de erro, retorna uma resposta de erro com o código 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro Interno no Servidor: " + e);
        }
    }
}

