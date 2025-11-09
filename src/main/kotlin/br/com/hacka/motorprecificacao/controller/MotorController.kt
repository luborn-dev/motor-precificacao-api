package br.com.hacka.motorprecificacao.controller

import br.com.hacka.motorprecificacao.dto.MotorDTO
import br.com.hacka.motorprecificacao.dto.MotorResponse
import br.com.hacka.motorprecificacao.service.MotorPrecificacaoService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável por consultar taxas de processamento via motor de IA.
 *
 * Endpoints:
 * - POST /api/motor/taxas - Consultar taxas baseado no perfil da empresa
 *
 * Exemplo de payload:
 * ```json
 * {
 *   "endereco": {
 *     "cep": "01310100",
 *     "rua": "Avenida Paulista",
 *     "complemento": "",
 *     "bairro": "Bela Vista",
 *     "cidade": "São Paulo",
 *     "estado": "SP"
 *   },
 *   "faturamentoMensal": "50000.00",
 *   "atividadePrincipal": [
 *     {
 *       "code": "6201502",
 *       "text": "Desenvolvimento de software"
 *     }
 *   ]
 * }
 * ```
 */
@RestController
@RequestMapping("/api/motor")
class MotorController(private val motorPrecificacaoService: MotorPrecificacaoService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Consulta as taxas de processamento baseado no perfil da empresa.
     *
     * @param request DTO com dados da empresa (endereço, faturamento, atividades)
     * @return ResponseEntity contendo as taxas de débito, crédito e voucher
     *
     * @throws Exception em caso de erro no processamento (500)
     */
    @PostMapping("/taxas")
    fun consultarTaxas(@RequestBody request: MotorDTO): ResponseEntity<MotorResponse> {
        logger.info("Requisição recebida para consultar taxas do motor IA")
        logger.debug("Dados da requisição: ${request.endereco.cidade}, Faturamento: ${request.faturamentoMensal}")

        val response = motorPrecificacaoService.consultarTaxasIA(request)

        logger.info("Taxas consultadas com sucesso - Débito: ${response.debito.size}, Crédito: ${response.credito.size}")

        return ResponseEntity.ok(response)
    }
}

