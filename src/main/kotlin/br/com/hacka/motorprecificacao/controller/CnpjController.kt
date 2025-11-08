package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.service.CnpjService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//cControlador responsável por consultar informações de empresas usando o CNPJ.

@RestController
@RequestMapping("/api/cnpj")
class CnpjController(
    private val cnpjService: CnpjService
) {

    @GetMapping("/{cnpj}")
    fun getCnpj(@PathVariable cnpj: String): ResponseEntity<Any> {
        return try {
            val response = cnpjService.consultarCnpj(cnpj)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }
}

