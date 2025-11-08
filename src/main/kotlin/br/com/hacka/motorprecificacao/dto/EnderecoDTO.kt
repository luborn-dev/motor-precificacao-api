package br.com.hacka.motorprecificacao.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO que mapeia os dados do endereço retornados pela API ViaCEP.
 *
 * Utiliza @SerializedName para mapear os nomes dos campos JSON para os nomes das propriedades Kotlin.
 */
data class EnderecoDTO(
    @SerializedName("cep")
    val cep: String,
    @SerializedName("logradouro")
    val rua: String,
    @SerializedName("complemento")
    val complemento: String,
    @SerializedName("bairro")
    val bairro: String,
    @SerializedName("localidade")
    val cidade: String,
    @SerializedName("uf")
    val estado: String,
    @SerializedName("ibge")
    val ibge: String? = null,
    @SerializedName("gia")
    val gia: String? = null,
    @SerializedName("ddd")
    val ddd: String? = null,
    @SerializedName("siafi")
    val siafi: String? = null
)

