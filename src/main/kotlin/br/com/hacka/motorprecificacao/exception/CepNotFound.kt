package br.com.hacka.motorprecificacao.exception

/**
 * Exceção lançada quando um CEP não é encontrado na API externa.
 */
class CepNotFoundException(
    val cep: String,
    message: String = "CEP não encontrado: $cep"
) : RuntimeException(message)

