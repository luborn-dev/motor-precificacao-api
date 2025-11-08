package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.dto.EnderecoResponse
import br.com.hacka.motorprecificacao.service.CepService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cep")
class CepController(private val cepService: CepService) {

    @GetMapping("/{cep}")
    fun consultarCep(@PathVariable cep: String): ResponseEntity<EnderecoResponse> {
        return try {
            val endereco = cepService.consultarCep(cep)
            ResponseEntity.ok(endereco)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

}

