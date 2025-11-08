package br.com.hacka.motorprecificacao.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Handler global para tratamento de exceções na aplicação.
 * Centraliza o tratamento de erros e retorna respostas padronizadas.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Trata exceções de CEP inválido.
     */
    @ExceptionHandler(InvalidCepException::class)
    fun handleInvalidCepException(e: InvalidCepException): ResponseEntity<ErrorResponse> {
        logger.warn("CEP inválido fornecido: ${e.cep}")
        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = e.message ?: "CEP inválido"
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    /**
     * Trata exceções de CEP não encontrado.
     */
    @ExceptionHandler(CepNotFoundException::class)
    fun handleCepNotFoundException(e: CepNotFoundException): ResponseEntity<ErrorResponse> {
        logger.info("CEP não encontrado na API externa: ${e.cep}")
        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = e.message ?: "CEP não encontrado"
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    /**
     * Trata exceções de CNPJ inválido.
     */
    @ExceptionHandler(InvalidCnpjException::class)
    fun handleInvalidCnpjException(e: InvalidCnpjException): ResponseEntity<ErrorResponse> {
        logger.warn("CNPJ inválido fornecido: ${e.cnpj}")
        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = e.message ?: "CNPJ inválido"
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    /**
     * Trata exceções de CNPJ não encontrado.
     */
    @ExceptionHandler(CnpjNotFoundException::class)
    fun handleCnpjNotFoundException(e: CnpjNotFoundException): ResponseEntity<ErrorResponse> {
        logger.info("CNPJ não encontrado na API externa: ${e.cnpj}")
        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = e.message ?: "CNPJ não encontrado"
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    /**
     * Trata exceções de API externa.
     */
    @ExceptionHandler(ExternalApiException::class)
    fun handleExternalApiException(e: ExternalApiException): ResponseEntity<ErrorResponse> {
        logger.error("Erro ao comunicar com API ${e.service}: Status ${e.statusCode}", e)
        val error = ErrorResponse(
            status = HttpStatus.BAD_GATEWAY.value(),
            message = e.message ?: "Erro ao comunicar com serviço externo"
        )
        return ResponseEntity(error, HttpStatus.BAD_GATEWAY)
    }

    /**
     * Trata exceções genéricas não capturadas pelos handlers específicos.
     */
    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Erro interno não tratado", e)
        val error = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Erro interno do servidor. Por favor, tente novamente mais tarde."
        )
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

