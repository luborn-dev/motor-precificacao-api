package br.com.hacka.motorprecificacao.dto

/**
 * DTO de resposta com as informações de endereço padronizadas para retorno na API.
 *
 * Este DTO é utilizado no endpoint GET /api/cep/{cep}.
 */
data class EnderecoResponse(
    /** CEP no formato XXXXX-XXX */
    val cep: String,
    /** Nome da rua/logradouro */
    val rua: String,
    /** Complemento do endereço (apto, sala, etc) */
    val complemento: String,
    /** Bairro */
    val bairro: String,
    /** Cidade/Município */
    val cidade: String,
    /** Sigla do estado (UF) */
    val estado: String,
    /** Código DDD telefônico */
    val ddd: String? = null
)

