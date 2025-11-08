package br.com.hacka.motorprecificacao.dto

// DTO que representa os dados de entrada para cálculo das taxas pelo motor de IA.

data class MotorDTO (
    val endereco: EnderecoDTO,
    val faturamentoMensal: String,
    val atividadePrincipal: List<AtividadePrincipal>
)

