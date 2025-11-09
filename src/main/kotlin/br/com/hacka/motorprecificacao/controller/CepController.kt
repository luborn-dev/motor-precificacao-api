package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.dto.EnderecoResponse
import br.com.hacka.motorprecificacao.service.CepService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável por consultar endereços a partir de um CEP.
 *
 * Endpoints:
 * - GET /api/cep/{cep} - Consultar endereço por CEP
 *
 * Exemplos de uso:
 * - GET /api/cep/01310100 - CEP sem formatação
 * - GET /api/cep/01310-100 - CEP com hífen
 */
@RestController
@RequestMapping("/api/cep")
class CepController(private val cepService: CepService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Consulta o endereço para um CEP específico.
     *
     * @param cep CEP a consultar (com ou sem formatação)
     * @return ResponseEntity com os dados do endereço ou erro
     *
     * @throws InvalidCepException se CEP tiver formato inválido (400)
     * @throws CepNotFoundException se CEP não for encontrado (404)
     * @throws ExternalApiException se houver erro na API externa (502)
     */
    @GetMapping("/{cep}")
    fun consultarCep(@PathVariable cep: String): ResponseEntity<EnderecoResponse> {
        logger.info("Requisição recebida para consultar CEP: $cep")

        val endereco = cepService.consultarCep(cep)
        logger.info("CEP consultado com sucesso: ${endereco.cidade}, ${endereco.estado}")

        return ResponseEntity.ok(endereco)
    }
}

