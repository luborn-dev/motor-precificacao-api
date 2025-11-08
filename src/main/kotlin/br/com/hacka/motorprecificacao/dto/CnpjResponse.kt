package br.com.hacka.motorprecificacao.dto

// Classe de resposta que representa os dados retornados na consulta de CNPJ.

data class CnpjResponse(
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
