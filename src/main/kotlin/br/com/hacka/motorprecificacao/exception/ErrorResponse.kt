package br.com.hacka.motorprecificacao.exception

// Classe que representa a estrutura padrão de respostas de erro da API.

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

