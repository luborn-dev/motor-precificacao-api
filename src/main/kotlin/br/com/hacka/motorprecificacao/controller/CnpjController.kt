package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.dto.CnpjResponse
import br.com.hacka.motorprecificacao.service.CnpjService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável por consultar informações de empresas usando o CNPJ.
 *
 * Endpoints:
 * - GET /api/cnpj/{cnpj} - Consultar dados da empresa por CNPJ
 *
 * Exemplos de uso:
 * - GET /api/cnpj/00000000000191 - CNPJ sem formatação
 * - GET /api/cnpj/00.000.000/0001-91 - CNPJ com formatação
 */
@RestController
@RequestMapping("/api/cnpj")
class CnpjController(
    private val cnpjService: CnpjService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Consulta os dados de uma empresa pelo CNPJ.
     *
     * @param cnpj CNPJ a consultar (com ou sem formatação)
     * @return ResponseEntity com os dados da empresa ou erro
     *
     * @throws InvalidCnpjException se CNPJ tiver formato inválido (400)
     * @throws CnpjNotFoundException se CNPJ não for encontrado (404)
     * @throws ExternalApiException se houver erro na API externa (502)
     */
    @GetMapping("/{cnpj}")
    fun getCnpj(@PathVariable cnpj: String): ResponseEntity<CnpjResponse> {
        logger.info("Requisição recebida para consultar CNPJ: $cnpj")

        val response = cnpjService.consultarCnpj(cnpj)
        logger.info("CNPJ consultado com sucesso: ${response.nome}")

        return ResponseEntity.ok(response)
    }
}

