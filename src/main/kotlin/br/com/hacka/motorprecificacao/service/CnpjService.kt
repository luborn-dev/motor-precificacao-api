
package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.CnpjDTO
import br.com.hacka.motorprecificacao.dto.CnpjResponse
import br.com.hacka.motorprecificacao.exception.CnpjNotFoundException
import br.com.hacka.motorprecificacao.exception.ExternalApiException
import br.com.hacka.motorprecificacao.exception.InvalidCnpjException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * Serviço responsável por consultar e validar CNPJs via API externa (ReceitaWS).
 *
 * Funcionalidades:
 * - Consulta de CNPJ com validação
 * - Limpeza de formatação de CNPJ
 * - Conversão de DTOs
 * - Tratamento de erros específicos
 */
@Service
class CnpjService(
    @Value("\${receitaws.api.url:https://www.receitaws.com.br/v1/cnpj}")
    private val apiUrl: String,
    @Value("\${receitaws.api.timeout:10}")
    private val timeoutSeconds: Long
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val client = OkHttpClient.Builder()
        .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    companion object {
        private const val CNPJ_PATTERN = "\\d{14}"
        private const val CNPJ_CLEAN_PATTERN = "[^0-9]"
    }

    /**
     * Consulta informações de empresa a partir de um CNPJ.
     *
     * @param cnpj CNPJ a ser consultado (com ou sem formatação)
     * @return CnpjResponse contendo os dados da empresa
     * @throws InvalidCnpjException se o CNPJ for inválido
     * @throws CnpjNotFoundException se o CNPJ não for encontrado na API
     * @throws ExternalApiException se houver erro ao comunicar com a API
     */
    fun consultarCnpj(cnpj: String): CnpjResponse {
        logger.debug("Iniciando consulta de CNPJ: $cnpj")

        val cnpjLimpo = normalizarCnpj(cnpj)

        if (!isValidCnpj(cnpjLimpo)) {
            logger.warn("CNPJ inválido fornecido: $cnpj")
            throw InvalidCnpjException(cnpj)
        }

        return consultarCnpjNaApi(cnpjLimpo)
    }

    /**
     * Remove caracteres especiais do CNPJ, mantendo apenas dígitos.
     */
    private fun normalizarCnpj(cnpj: String): String {
        return cnpj.replace(Regex(CNPJ_CLEAN_PATTERN), "").trim()
    }

    /**
     * Valida se o CNPJ possui 14 dígitos.
     */
    private fun isValidCnpj(cnpj: String): Boolean {
        return cnpj.matches(Regex(CNPJ_PATTERN))
    }

    /**
     * Faz a requisição à API ReceitaWS.
     */
    private fun consultarCnpjNaApi(cnpjLimpo: String): CnpjResponse {
        return try {
            val request = Request.Builder()
                .url("$apiUrl/$cnpjLimpo")
                .addHeader("Accept", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                val statusCode = response.code

                if (!response.isSuccessful) {
                    logger.error("Erro ao consultar CNPJ na API. Status: $statusCode")
                    throw ExternalApiException(statusCode, "ReceitaWS", "Status HTTP $statusCode")
                }

                val body = response.body?.string()
                    ?: throw ExternalApiException(statusCode, "ReceitaWS", "Resposta vazia")

                processarResposta(cnpjLimpo, body)
            }
        } catch (e: CnpjNotFoundException) {
            // Relançar exceção específica de não encontrado
            logger.info("CNPJ não encontrado: $cnpjLimpo")
            throw e
        } catch (e: ExternalApiException) {
            logger.error("Erro na comunicação com ReceitaWS", e)
            throw e
        } catch (e: Exception) {
            logger.error("Erro inesperado ao consultar CNPJ", e)
            throw ExternalApiException(null, "ReceitaWS", e.message ?: "Erro desconhecido")
        }
    }

    /**
     * Processa a resposta da API, verificando se o CNPJ foi encontrado.
     */
    private fun processarResposta(cnpjLimpo: String, body: String): CnpjResponse {
        // API retorna { "status": "ERROR" } quando não encontra
        if (body.contains("\"status\":\"ERROR\"")) {
            logger.info("CNPJ não encontrado na API: $cnpjLimpo")
            throw CnpjNotFoundException(cnpjLimpo)
        }

        return try {
            val cnpjDTO = gson.fromJson(body, CnpjDTO::class.java)
            cnpjDTO.toResponse()
        } catch (e: JsonSyntaxException) {
            logger.error("Erro ao desserializar resposta da API", e)
            throw ExternalApiException(200, "ReceitaWS", "Resposta inválida")
        }
    }

    /**
     * Converte CnpjDTO em CnpjResponse.
     */
    private fun CnpjDTO.toResponse(): CnpjResponse {
        return CnpjResponse(
            cnpj = this.cnpj,
            nome = this.nome,
            fantasia = this.fantasia,
            telefone = this.telefone,
            email = this.email,
            situacao = this.situacao,
            logradouro = this.logradouro,
            numero = this.numero,
            municipio = this.municipio,
            uf = this.uf,
            cep = this.cep,
            atividade_principal = this.atividade_principal
        )
    }
}
 