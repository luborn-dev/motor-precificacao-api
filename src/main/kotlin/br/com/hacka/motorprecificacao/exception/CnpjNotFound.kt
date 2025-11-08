package br.com.hacka.motorprecificacao.exception

/**
 * Exceção lançada quando um CNPJ não é encontrado na API externa.
 */
class CnpjNotFoundException(
    val cnpj: String,
    message: String = "CNPJ não encontrado: $cnpj"
) : RuntimeException(message)

