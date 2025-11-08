package br.com.hacka.motorprecificacao.dto

// DTO que contém os dados cadastrais de uma empresa consultada pelo CNPJ.

data class CnpjDTO(
    val cnpj: String,
    val nome: String,
    val fantasia: String?,
    val telefone: String?,
    val email: String?,
    val situacao: String?,
    val logradouro: String?,
    val numero: String?,
    val municipio: String?,
    val uf: String?,
    val cep: String?,
    val atividade_principal: List<AtividadePrincipal>
)