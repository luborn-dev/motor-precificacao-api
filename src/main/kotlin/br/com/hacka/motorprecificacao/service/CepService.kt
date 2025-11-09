package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.EnderecoDTO
import br.com.hacka.motorprecificacao.dto.EnderecoResponse
import br.com.hacka.motorprecificacao.exception.CepNotFoundException
import br.com.hacka.motorprecificacao.exception.ExternalApiException
import br.com.hacka.motorprecificacao.exception.InvalidCepException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * Serviço responsável por consultar e validar CEPs via API externa (ViaCEP).
 *
 * Funcionalidades:
 * - Consulta de CEP com validação
 * - Limpeza de formatação de CEP
 * - Conversão de DTOs
 * - Tratamento de erros específicos
 */
@Service
class CepService(
    @Value("\${viacep.api.url:https://viacep.com.br/ws}")
    private val apiUrl: String,
    @Value("\${viacep.api.timeout:10}")
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
        private const val CEP_PATTERN = "\\d{8}"
        private const val CEP_CLEAN_PATTERN = "[^0-9]"
    }

    /**
     * Consulta endereço a partir de um CEP.
     *
     * @param cep CEP a ser consultado (com ou sem formatação)
     * @return EnderecoResponse contendo os dados do endereço
     * @throws InvalidCepException se o CEP for inválido
     * @throws CepNotFoundException se o CEP não for encontrado na API
     * @throws ExternalApiException se houver erro ao comunicar com a API
     */
    fun consultarCep(cep: String): EnderecoResponse {
        logger.debug("Iniciando consulta de CEP: $cep")

        val cepLimpo = normalizarCep(cep)

        if (!isValidCep(cepLimpo)) {
            logger.warn("CEP inválido fornecido: $cep")
            throw InvalidCepException(cep)
        }

        return consultarCepNaApi(cepLimpo)
    }

    /**
     * Remove caracteres especiais do CEP, mantendo apenas dígitos.
     */
    private fun normalizarCep(cep: String): String {
        return cep.replace(Regex(CEP_CLEAN_PATTERN), "")
    }

    /**
     * Valida se o CEP possui 8 dígitos.
     */
    private fun isValidCep(cep: String): Boolean {
        return cep.matches(Regex(CEP_PATTERN))
    }

    /**
     * Faz a requisição à API ViaCEP.
     */
    private fun consultarCepNaApi(cepLimpo: String): EnderecoResponse {
        return try {
            val request = Request.Builder()
                .url("$apiUrl/$cepLimpo/json")
                .build()

            client.newCall(request).execute().use { response ->
                val statusCode = response.code

                if (!response.isSuccessful) {
                    logger.error("Erro ao consultar CEP na API. Status: $statusCode")
                    throw ExternalApiException(statusCode, "ViaCEP", "Status HTTP $statusCode")
                }

                val body = response.body?.string()
                    ?: throw ExternalApiException(statusCode, "ViaCEP", "Resposta vazia")

                procesarResposta(cepLimpo, body)
            }
        } catch (e: CepNotFoundException) {
            // Relançar exceção específica de não encontrado
            logger.info("CEP não encontrado: $cepLimpo")
            throw e
        } catch (e: ExternalApiException) {
            logger.error("Erro na comunicação com ViaCEP", e)
            throw e
        } catch (e: Exception) {
            logger.error("Erro inesperado ao consultar CEP", e)
            throw ExternalApiException(null, "ViaCEP", e.message ?: "Erro desconhecido")
        }
    }

    /**
     * Processa a resposta da API, verificando se o CEP foi encontrado.
     */
    private fun procesarResposta(cepLimpo: String, body: String): EnderecoResponse {
        if (body.contains("\"erro\"") || body.contains("\"erro\": true")) {
            logger.info("CEP não encontrado na API: $cepLimpo")
            throw CepNotFoundException(cepLimpo)
        }

        return try {
            val enderecoDTO = gson.fromJson(body, EnderecoDTO::class.java)
            enderecoDTO.toResponse()
        } catch (e: JsonSyntaxException) {
            logger.error("Erro ao desserializar resposta da API", e)
            throw ExternalApiException(200, "ViaCEP", "Resposta inválida")
        }
    }

    /**
     * Converte EnderecoDTO em EnderecoResponse.
     */
    private fun EnderecoDTO.toResponse(): EnderecoResponse {
        return EnderecoResponse(
            cep = this.cep,
            rua = this.rua,
            complemento = this.complemento,
            bairro = this.bairro,
            cidade = this.cidade,
            estado = this.estado,
            ddd = this.ddd
        )
    }
}

