package br.com.hacka.motorprecificacao.dto

/**
 * DTO de resposta que representa os dados retornados na consulta de CNPJ.
 *
 * Este DTO é utilizado no endpoint GET /api/cnpj/{cnpj}.
 */
data class CnpjResponse(
    /** CNPJ da empresa */
    val cnpj: String,
    /** Razão social */
    val nome: String,
    /** Nome fantasia */
    val fantasia: String?,
    /** Telefone da empresa */
    val telefone: String?,
    /** Email da empresa */
    val email: String?,
    /** Situação da empresa (ATIVA, INATIVA, etc) */
    val situacao: String?,
    /** Logradouro do endereço */
    val logradouro: String?,
    /** Número do endereço */
    val numero: String?,
    /** Município */
    val municipio: String?,
    /** Unidade Federativa (estado) */
    val uf: String?,
    /** CEP da empresa */
    val cep: String?,
    /** Lista de atividades principais (CNAE) */
    val atividade_principal: List<AtividadePrincipal>
)
