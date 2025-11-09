package br.com.hacka.motorprecificacao.exception

/**
 * Exceção lançada quando um CNPJ possui formato inválido.
 */
class InvalidCnpjException(
    val cnpj: String,
    message: String = "CNPJ inválido: $cnpj. Deve conter 14 dígitos."
) : RuntimeException(message)

