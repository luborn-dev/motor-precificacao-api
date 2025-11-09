package br.com.hacka.motorprecificacao.exception

/**
 * Classe que representa a estrutura padrão de respostas de erro da API.
 * Inclui status HTTP, mensagem de erro e timestamp para auditoria.
 */
data class ErrorResponse(
    /** Código de status HTTP */
    val status: Int,
    /** Mensagem descritiva do erro */
    val message: String,
    /** Timestamp do erro em milissegundos desde epoch */
    val timestamp: Long = System.currentTimeMillis()
)

