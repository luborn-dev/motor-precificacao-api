package br.com.hacka.motorprecificacao.dto

// Classe que representa a atividade principal de uma empresa com código e descrição. CNAE

data class AtividadePrincipal(
    val code: String,
    val text: String,
)