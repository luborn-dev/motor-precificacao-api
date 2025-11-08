package br.com.hacka.motorprecificacao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Aplicação principal do Motor de Precificação.
 *
 * Sistema responsável por:
 * - Consultar endereços via CEP (API ViaCEP)
 * - Consultar informações de empresas via CNPJ (API ReceitaWS)
 * - Consultar taxas de processamento via Motor IA
 *
 * Endpoints disponíveis:
 * - GET /api/cep/{cep} - Consultar endereço por CEP
 * - GET /api/cnpj/{cnpj} - Consultar dados da empresa por CNPJ
 * - POST /api/motor/taxas - Consultar taxas via Motor IA
 */
@SpringBootApplication
class MotorPrecificacaoApplication

fun main(args: Array<String>) {
    runApplication<MotorPrecificacaoApplication>(*args)
}
