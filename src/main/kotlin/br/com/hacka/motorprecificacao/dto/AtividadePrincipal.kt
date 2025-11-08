package br.com.hacka.motorprecificacao.dto

/**
 * DTO que representa a atividade principal de uma empresa com código e descrição.
 *
 * Segue a classificação CNAE (Classificação Nacional de Atividades Econômicas).
 */
data class AtividadePrincipal(
    /** Código CNAE da atividade */
    val code: String,
    /** Descrição da atividade */
    val text: String
)