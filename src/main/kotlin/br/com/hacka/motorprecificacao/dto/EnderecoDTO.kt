package br.com.hacka.motorprecificacao.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO que mapeia os dados do endereço retornados pela API ViaCEP.
 *
 * Utiliza @SerializedName para mapear os nomes dos campos JSON para os nomes das propriedades Kotlin.
 */
data class EnderecoDTO(
    val cep: String,
    @SerializedName("logradouro")
    val rua: String,
    val complemento: String,
    val bairro: String,
    @SerializedName("localidade")
    val cidade: String,
    @SerializedName("uf")
    val estado: String,
    val ibge: String? = null,
    val gia: String? = null,
    val ddd: String? = null,
    val siafi: String? = null
)

