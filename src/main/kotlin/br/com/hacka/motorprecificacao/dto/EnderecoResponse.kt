package br.com.hacka.motorprecificacao.dto

// Classe de resposta com as informações de endereço padronizadas para retorno na API.

data class EnderecoResponse(
    val cep: String,
    val rua: String,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val ddd: String? = null
)

