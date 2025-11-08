package br.com.hacka.motorprecificacao.dto

data class EnderecoResponse(
    val cep: String,
    val rua: String,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val ddd: String? = null
)

