package br.com.hacka.motorprecificacao.exception

/**
 * Exceção lançada quando um CEP possui formato inválido.
 */
class InvalidCepException(
    val cep: String,
    message: String = "CEP inválido: $cep. Deve conter 8 dígitos."
) : RuntimeException(message)

