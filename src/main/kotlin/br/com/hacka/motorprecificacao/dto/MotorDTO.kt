package br.com.hacka.motorprecificacao.dto

/**
 * DTO que representa os dados de entrada para cálculo das taxas pelo motor de IA.
 *
 * Este DTO é utilizado no endpoint POST /api/motor/taxas.
 */
data class MotorDTO(
    /** Dados do endereço da empresa */
    val endereco: EnderecoDTO,
    /** Faturamento mensal da empresa */
    val faturamentoMensal: String,
    /** Lista de atividades principais (CNAE) da empresa */
    val atividadePrincipal: List<AtividadePrincipal>
)

