package br.com.hacka.motorprecificacao.exception

/**
 * Exceção lançada quando há erro ao comunicar com uma API externa.
 */
class ExternalApiException(
    val statusCode: Int?,
    val service: String,
    message: String
) : RuntimeException("Erro ao consultar $service. Status: $statusCode. Detalhes: $message")

